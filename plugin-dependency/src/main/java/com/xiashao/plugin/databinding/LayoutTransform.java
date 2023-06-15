package com.xiashao.plugin.databinding;

import static com.android.build.api.transform.QualifiedContent.DefaultContentType.CLASSES;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.AppExtension;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class LayoutTransform extends Transform {

    private final Project mProject;

    private HashMap<String, String> mDataBindingClassMap = new HashMap<>();

    private HashMap<String, String> mDataBindingImplClassMap = new HashMap<>();

    private HashMap<String, String> mOverrideDataBindingClassMap = new HashMap<>();

    private HashMap<String, String> mOverrideDataBindingClassImplMap = new HashMap<>();

    private Map<File, File> mFileOutputDir = new HashMap<>();

    private Map<File, File> mJarOutputDir = new HashMap<>();

    public LayoutTransform(Project project) {
        this.mProject = project;
    }

    @Override
    public String getName() {
        return "LayoutTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        HashSet<QualifiedContent.ContentType> set = new HashSet<>();
        set.add(CLASSES);
        return set;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        HashSet set = new HashSet();
        set.add(QualifiedContent.Scope.PROJECT);
        set.add(QualifiedContent.Scope.SUB_PROJECTS);
        set.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);
        return set;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }


    @Override
    public void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        AppExtension appExtension = (AppExtension) mProject.getExtensions().getByType(AppExtension.class);
        String applicationId = appExtension.getDefaultConfig().getApplicationId();
        String packageName = applicationId.replace('.', '/') + "/databinding";
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        if (outputProvider != null) {
            outputProvider.deleteAll();
        }
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        if (inputs != null) {
            for (TransformInput transformInput : inputs) {
                Collection<DirectoryInput> directoryInputs = transformInput.getDirectoryInputs();
                for (DirectoryInput directoryInput : directoryInputs) {
                    File file = directoryInput.getFile();
                    findOverrideDataBindingClassInDir(file, packageName);
                    File contentLocation = outputProvider.getContentLocation(directoryInput.getName(),
                            directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
                    mFileOutputDir.put(file, contentLocation);
                }
                Collection<JarInput> jarInputs = transformInput.getJarInputs();
                for (JarInput jarInput : jarInputs) {
                    File file = jarInput.getFile();
                    findDataBindClassInJar(file);
                    File contentLocation = outputProvider.getContentLocation(jarInput.getName(),
                            jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                    mJarOutputDir.put(file, contentLocation);
                }
            }
        }

        findDataBindingClassImpl();
        findOverrideDataBindingClassImpl();
        override();
    }

    private void findOverrideDataBindingClassImpl() throws IOException {
        for (File file : mFileOutputDir.keySet()) {
            findOverrideDataBindingClassImpl(file);
        }
    }

    private void findOverrideDataBindingClassImpl(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File childFile : files) {
                findOverrideDataBindingClassImpl(childFile);
            }
        } else {
            if (file.getName().endsWith(".class")) {
                ClassReader classReader = new ClassReader(new FileInputStream(file));
                DataBindingImplClassScanner scanner = new DataBindingImplClassScanner(mOverrideDataBindingClassMap);
                classReader.accept(scanner, ClassReader.EXPAND_FRAMES);
                String className = classReader.getClassName();
                if (scanner.isDataBindingImplClass()) {
                    String simpleName = className.substring(className.lastIndexOf('/') + 1);
                    mOverrideDataBindingClassImplMap.put(simpleName, className);
                }
            }
        }
    }

    private void findDataBindingClassImpl() throws IOException {
        for (File file : mJarOutputDir.keySet()) {
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.endsWith("class")) {
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    ClassReader classReader = new ClassReader(inputStream);
                    DataBindingImplClassScanner scanner = new DataBindingImplClassScanner(mDataBindingClassMap);
                    classReader.accept(scanner, ClassReader.EXPAND_FRAMES);
                    String className = classReader.getClassName();
                    if (scanner.isDataBindingImplClass()) {
                        String simpleName = className.substring(className.lastIndexOf('/') + 1);
                        mDataBindingImplClassMap.put(simpleName, className);
                    }
                }
            }
        }
    }

    private void override() throws IOException {
        overrideFile();
        overrideJar();
    }

    private void overrideFile() throws IOException {
        AppExtension extensions = (AppExtension) mProject.getExtensions().getByType(AppExtension.class);
        String applicationId = extensions.getDefaultConfig().getApplicationId();
        String excludePackagePath = applicationId.replace('.', File.separatorChar);
        for (File file : mFileOutputDir.keySet()) {
            overrideFile(file, excludePackagePath, mFileOutputDir.get(file).getAbsolutePath(), new StringBuilder());
        }
    }

    private void overrideFile(File file, String excludePackageName, String dstRootDir, StringBuilder currentDirRelativeDir) throws IOException {
        String fileName = file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            currentDirRelativeDir.append(File.separatorChar);
            currentDirRelativeDir.append(fileName);
            File targetDir = new File(dstRootDir + currentDirRelativeDir);
            if (!targetDir.exists()) targetDir.mkdirs();
            for (File childFile : files) {
                overrideFile(childFile, excludePackageName, dstRootDir, currentDirRelativeDir);
            }
            currentDirRelativeDir.delete(currentDirRelativeDir.length() - fileName.length() - 1, currentDirRelativeDir.length());
        } else {
            try {
                currentDirRelativeDir.append(File.separatorChar);
                currentDirRelativeDir.append(fileName);
                String targetPath = dstRootDir + currentDirRelativeDir;
                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = !file.getPath().contains(excludePackageName) && file.getName().endsWith(".class") ? overrideClass(inputStream) : IOUtils.toByteArray(inputStream);
                File targetFile = new File(targetPath);
                if (!targetFile.exists())
                    targetFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(targetFile);
                IOUtils.write(bytes, outputStream);
                inputStream.close();
                outputStream.close();
            } finally {
                currentDirRelativeDir.delete(currentDirRelativeDir.length() - fileName.length() - 1, currentDirRelativeDir.length());
            }

        }
    }

    private void overrideJar() throws IOException {
        for (File file : mJarOutputDir.keySet()) {
            JarFile jarFile = new JarFile(file);
            File targetFile = new File(mJarOutputDir.get(file).getAbsolutePath());
            if (targetFile.exists()) {
                targetFile.delete();
            }
            targetFile.createNewFile();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(targetFile));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                ZipEntry zipEntry = new ZipEntry(name);
                jarOutputStream.putNextEntry(zipEntry);
                if (name.endsWith("class")) {
                    IOUtils.write(overrideClass(inputStream), jarOutputStream);
                } else {
                    IOUtils.write(IOUtils.toByteArray(inputStream), jarOutputStream);
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            jarFile.close();
        }
    }

    private byte[] overrideClass(InputStream inputStream) throws IOException {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassOverrider classOverrider = new ClassOverrider(classWriter, mOverrideDataBindingClassMap,
                mDataBindingClassMap);
        classReader.accept(classOverrider, ClassReader.EXPAND_FRAMES);
        ClassReader classImplReader = new ClassReader(classWriter.toByteArray());
        ClassWriter classImplWriter = new ClassWriter(classImplReader, ClassWriter.COMPUTE_MAXS);
        ClassOverrider classImplOverrider = new ClassOverrider(classImplWriter, mOverrideDataBindingClassImplMap,
                mDataBindingImplClassMap);
        classImplReader.accept(classImplOverrider, ClassReader.EXPAND_FRAMES);
        return classImplWriter.toByteArray();
    }


    private void findDataBindClassInJar(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if (name.endsWith("class")) {
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                ClassReader classReader = new ClassReader(inputStream);
                DataBindingClassScanner bindObjectScanner = new DataBindingClassScanner();
                classReader.accept(bindObjectScanner, ClassReader.EXPAND_FRAMES);
                String className = classReader.getClassName();
                if (bindObjectScanner.isDataBindingClass()) {
                    String simpleName = className.substring(className.lastIndexOf('/') + 1);
                    mDataBindingClassMap.put(simpleName, className);
                }
            }
        }
    }


    private void findOverrideDataBindingClassInDir(File file, String packageName) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    findOverrideDataBindingClassInDir(childFile, packageName);
                }
            }
        } else {
            if (file.getName().endsWith("class")) {
                ClassReader classReader = new ClassReader(new FileInputStream(file));
                DataBindingClassScanner bindObjectScanner = new DataBindingClassScanner();
                classReader.accept(bindObjectScanner, ClassReader.EXPAND_FRAMES);
                String className = classReader.getClassName();
                if (bindObjectScanner.isDataBindingClass() && className.startsWith(packageName)) {
                    String simpleName = className.substring(className.lastIndexOf('/') + 1);
                    mOverrideDataBindingClassMap.put(simpleName, className);
                }
            }
        }
    }
}
