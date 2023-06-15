package com.xiashao.plugin.databinding;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class ClassOverrider extends ClassVisitor {

    private Map<String, String> mOverrideClassMap;
    private Map<String, String> mClassMap;

    public ClassOverrider(ClassVisitor classVisitor, Map<String, String> overrideDataBindingClassMap, Map<String, String> dataBindingClassMap) {
        super(Opcodes.ASM6, classVisitor);
        mOverrideClassMap = overrideDataBindingClassMap;
        mClassMap = dataBindingClassMap;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        String simpleName = descriptor.substring(descriptor.lastIndexOf('/') + 1);
        if (simpleName.charAt(simpleName.length() - 1) == ';') {
            simpleName = simpleName.substring(0, simpleName.length() - 1);
        }
        if (mClassMap.containsKey(simpleName) && descriptor.contains(mClassMap.get(simpleName)) && mOverrideClassMap.containsKey(simpleName)) {
            descriptor = descriptor.replace(mClassMap.get(simpleName), mOverrideClassMap.get(simpleName));
        }
        return super.visitField(access, name, descriptor, signature, value);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        for (String simpleName : mClassMap.keySet()) {
            if (descriptor.contains(simpleName) && mOverrideClassMap.containsKey(simpleName)) {
                descriptor = descriptor.replace(mClassMap.get(simpleName), mOverrideClassMap.get(simpleName));
                break;
            }
        }
        return new MethodOverrider(super.visitMethod(access, name, descriptor, signature, exceptions), mClassMap, mOverrideClassMap);
    }
}
