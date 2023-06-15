package com.xiashao.plugin.databinding;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class MethodOverrider extends MethodVisitor {
    private Map<String, String> classMap;
    private Map<String, String> overrideClassMap;

    public MethodOverrider(MethodVisitor methodVisitor, Map<String, String> dataBindingClassMap, Map<String, String> overrideDataBindingClassMap) {
        super(Opcodes.ASM6, methodVisitor);
        this.classMap = dataBindingClassMap;
        this.overrideClassMap = overrideDataBindingClassMap;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        for (String simpleName : classMap.keySet()) {
            if (overrideClassMap.containsKey(simpleName)) {
                if (descriptor.contains(simpleName)) {
                    descriptor = descriptor.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
                if (owner.contains(simpleName)) {
                    owner = owner.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
            }
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        for (String simpleName : classMap.keySet()) {
            if (descriptor.contains(simpleName) && overrideClassMap.containsKey(simpleName)) {
                descriptor = descriptor.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
            }
        }
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        for (String simpleName : classMap.keySet()) {
            if (overrideClassMap.containsKey(simpleName)) {
                if (type.contains(simpleName)) {
                    type = type.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
            }
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        for (String simpleName : classMap.keySet()) {
            if (overrideClassMap.containsKey(simpleName)) {
                if (descriptor.contains(simpleName)) {
                    descriptor = descriptor.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
            }
        }
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        for (String simpleName : classMap.keySet()) {
            if (overrideClassMap.containsKey(simpleName)) {
                if (descriptor.contains(simpleName)) {
                    descriptor = descriptor.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
                if (owner.contains(simpleName)) {
                    owner = owner.replace(classMap.get(simpleName), overrideClassMap.get(simpleName));
                }
            }
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
