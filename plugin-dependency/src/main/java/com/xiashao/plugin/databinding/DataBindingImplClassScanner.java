package com.xiashao.plugin.databinding;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class DataBindingImplClassScanner extends ClassVisitor {
    private boolean isDataBindingImplClass;
    private Map<String, String> mDataBindingClassImplMap;

    public DataBindingImplClassScanner(Map<String, String> map) {
        super(Opcodes.ASM6);
        mDataBindingClassImplMap = map;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (name.contains("databinding")) {
            String superSimpleName = superName.substring(superName.lastIndexOf('/') + 1);
            if (superName.equals(mDataBindingClassImplMap.get(superSimpleName))) {
                isDataBindingImplClass = true;
            }
        }
    }

    public boolean isDataBindingImplClass() {
        return isDataBindingImplClass;
    }

}
