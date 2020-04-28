package com.mjiayou.myplugin

import com.android.SdkConstants
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import org.gradle.api.Project

import java.lang.reflect.Modifier

public class MyInsert {
    private ClassPool classPool = ClassPool.getDefault()

    public void insertCode(String directoryInputFilePath, Project project) {
        log("insertCode | directoryInputFilePath =  " + directoryInputFilePath) // /Users/xin/Documents/Projects/TreCore/app/build/intermediates/javac/officialDebug/classes

        // classPool
        classPool.appendClassPath(directoryInputFilePath)
        classPool.importPackage("android.os.Bundle")
        project.android.bootClasspath.each {
            classPool.appendClassPath(it.absolutePath)
            log("insertCode | appendClassPath | it.absolutePath = " + it.absolutePath)
        }

        File directoryInputFile = new File(directoryInputFilePath)
        // 递归得到ALL文件 directoryInputFile.eachFileRecurse {
        // 递归得到CLASS文件 directoryInputFile.traverse(type: groovy.io.FileType.FILES, nameFilter: ~/.*\.class/) {
        directoryInputFile.eachFileRecurse {
            File file ->
                String filePath = file.absolutePath
                // log("insertCode | 111 | filePath = " + filePath)

                // 过滤掉 不是.class结尾的文件
                if (!filePath.endsWith(SdkConstants.DOT_CLASS)) {
                    return
                }
                // log("insertCode | 222 | filePath = " + filePath)

                // 过滤掉 R.class文件、BuildConfig.class文件、包含R$的文件、包含$的文件
                if (filePath.contains('R.class') || filePath.contains('BuildConfig.class') || filePath.contains('R$') || filePath.contains('$')) {
                    return
                }
                // log("insertCode | 333 | filePath = " + filePath)

                // 获取包含包名的类文件名
                def classNameWithPackage = filePath.replace(directoryInputFilePath, "")
                        .replace("\\", ".")
                        .replace("/", ".")
                        .replace(SdkConstants.DOT_CLASS, "")
                        .substring(1)
                // log("insertCode | 444 | classNameWithPackage = " + classNameWithPackage)

                // 写入代码
                CtClass ctClass = classPool.getCtClass(classNameWithPackage)
                if (ctClass.isFrozen()) {
                    ctClass.defrost()
                }
                CtMethod[] ctMethods = ctClass.getDeclaredMethods()
                log("insertCode | 555 | classNameWithPackage = " + classNameWithPackage + " | ctMethods.length = " + ctMethods.length)
                for (CtMethod ctMethod : ctMethods) {
                    String methodName = ctMethod.name
                    if (!ctMethod.isEmpty() && !Modifier.isNative(ctMethod.getModifiers())) {
                        // 编辑代码 android.util.Log.e("matengfei-MyMethodVisitor-byJavassist", this.getClass().getName() + "onCreate");
                        String code = "android.util.Log.d(\"matengfei-MyInsert-byJavassist\", \"" + classNameWithPackage + "\" + \" | \" + \"" + methodName + "\");"
                        ctMethod.insertBefore(code)
                        log("insertCode | 555 | insertBefore SUCCESS | classNameWithPackage = " + classNameWithPackage + " | methodName = " + methodName)
                    } else {
                        log("insertCode | 555 | insertBefore FAILURE | classNameWithPackage = " + classNameWithPackage + " | methodName = " + methodName + " | FAILURE ! FAILURE ! FAILURE ! FAILURE ! FAILURE ! FAILURE ! ")
                    }
                }
                ctClass.writeFile(directoryInputFilePath)
                ctClass.detach()
        }
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyInsert | " + msg)
    }
}
