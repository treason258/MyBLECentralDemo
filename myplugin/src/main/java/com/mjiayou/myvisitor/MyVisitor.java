package com.mjiayou.myvisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyVisitor extends ClassVisitor {

    private String className;
    private String superName;

    public MyVisitor(int api) {
        super(api);
    }

    public MyVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    /**
     * 首先执行
     *
     * @param version    jdk版本
     * @param access     类描述 public private
     * @param name       全类名 com/mjiayou/myvisitor/MyVisitor
     * @param signature  泛型信息 没有为空
     * @param superName  继承类的信息 org/objectweb/asm/ClassVisitor
     * @param interfaces 接口信息
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        log("visit | name = " + name + " | superName = " + superName);
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
    }

    /**
     * 访问方法
     *
     * @param access     方法描述 public protected
     * @param name       方法名
     * @param descriptor 方法返回类型
     * @param signature  泛型信息 没有为空
     * @param exceptions 异常信息 没有为空
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        log("visitMethod | name = " + name + " | superName = " + superName);
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (superName.equals("android/app/Activity")
                || superName.equals("android/support/v7/app/AppCompatActivity")
                || superName.equals("com/mjiayou/trecorelib/base/TCActivity")) {
            if (name != null && !name.equals("<init>")) {
                return new MyMethodVisitor(methodVisitor, superName, name);
            }
        }
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyVisitor | " + msg);
    }
}
