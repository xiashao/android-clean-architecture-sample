package com.xiashao.plugin.databinding;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DataBindingClassScanner extends ClassVisitor {
    private boolean isDataBindingClass;

    public DataBindingClassScanner() {
        super(Opcodes.ASM6);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (name.contains("databinding") && name.endsWith("Binding") && superName.equals("androidx/databinding/ViewDataBinding")) {
            isDataBindingClass = true;
        }
    }

    public boolean isDataBindingClass() {
        return isDataBindingClass;
    }

}
