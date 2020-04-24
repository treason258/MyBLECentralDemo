package com.mjiayou.myvisitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;

public class MyMethodVisitor extends MethodVisitor {

    private String className;
    private String methodName;

    public MyMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM5, methodVisitor);
        log("MyMethodVisitor | className = " + className + " | methodName = " + methodName);

        this.className = className;
        this.methodName = methodName;
    }

    /**
     * 方法前
     */
    @Override
    public void visitCode() {
        super.visitCode();
        log("visitCode | className = " + className + " | methodName = " + methodName);

        // Log.d("matengfei-MyMethodVisitor-byASM", getClass().getName() + " | method()");
        mv.visitLdcInsn("matengfei-MyMethodVisitor-byASM");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn(" | " + methodName + "()");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
    }

    /**
     * 方法后
     */
    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        log("visitInsn | className = " + className + " | methodName = " + methodName);
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyMethodVisitor | " + msg);
    }
}
