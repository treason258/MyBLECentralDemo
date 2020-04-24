package com.mjiayou.trecore.test.asm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.ToastUtils;

public class ASMActivity extends AppCompatActivity {

//    public ASMActivity() {
//        Log.d("123", getClass().getName() + " | <init>()");
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.e("MyMethodVisitor", "className = " + this.getClass().getName() + " | afterOnCreate");

//        mv.visitLdcInsn("MyMethodVisitor");
//        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//        mv.visitInsn(DUP);
//        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//        mv.visitLdcInsn("className = ");
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitVarInsn(ALOAD, 0);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitLdcInsn(" | afterOnCreate");
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(POP);

//        Log.d("matengfei-MyMethodVisitor-byASM", getClass().getName() + " | method()");

//        mv.visitLdcInsn("matengfei-MyMethodVisitor-byASM");
//        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//        mv.visitInsn(DUP);
//        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//        mv.visitVarInsn(ALOAD, 0);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitLdcInsn(" | method()");
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(POP);

        method111();
    }

    private void method111() {
        LogUtils.d("matengfei", "ASMActivity | method111");
        method222();
    }

    private void method222() {
        LogUtils.d("matengfei", "ASMActivity | method222");
        method444();
    }

    private void method333() {
        LogUtils.d("matengfei", "ASMActivity | method333");
        ToastUtils.show("method333");
    }

    private void method444() {
        LogUtils.d("matengfei", "ASMActivity | method444");
        method666();
    }

    private void method555() {
        LogUtils.d("matengfei", "ASMActivity | method555");
        method333();
    }

    private void method666() {
        LogUtils.d("matengfei", "ASMActivity | method666");
        method555();
    }
}
