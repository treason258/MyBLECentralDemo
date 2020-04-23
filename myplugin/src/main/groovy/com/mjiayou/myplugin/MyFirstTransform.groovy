package com.mjiayou.myplugin

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import javassist.ClassPool

public class MyFirstTransform extends Transform {

    // 字节码池
    def pool = ClassPool.default;
    // 工程，有所有class的路径
    Project project

    MyFirstTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "treason"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    // 修改字节码
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        // 准备工作，转化器
//        super.transform(transformInvocation)

        log("******************************** transform-start ********************************")
        log("000")

//        def startTime = System.currentTimeMillis()
//        outputProvider.deleteAll()
//        File jarFile = outputProvider.getContentLocation("h", getOutputTypes(), getScopes(), Format.JAR);
//        log("jarFile" + jarFile.getAbsolutePath());

//        project.android.bootClasspath.each {
//            log("000");
//            pool.appendClassPath(it.absolutePath)
//        }

        // 遍历上一个 transform 传递进来的文件
        // class --- jar
//        transformInvocation.inputs.each {
//            log("111111");

//            // 遍历jar包所有的类
//            it.jarInputs.each {
//                pool.inserClassPath(it.file.absolutePath)
//
//                // 设定输出参数，编译的输入与输出一一对应
//                def dest = transformInvocation.outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
//
//                // copy文件到输出
//                FileUtils.copyFile(it.file, dest)
//            }

//            // 所有的类，自己写的类
//            it.directoryInputs.each {
//                def preFileName = it.file.absolutePath
//                // 耗内存 优化apk 加载 class --> dex
//                pool.inserClassPath(preFileName)
//
//                // 修改class的代码
//                findTarget(it.file, preFileName)
//            }
//        }

        log("******************************** transform-end ********************************")
    }

    private void findTarget(File dir, String fileName) {
//        log("fileName = " + fileName);
        // 找到.class结尾的文件
        if (dir.isDirectory()) {
            dir.listFiles().each {
                findTarget(it, fileName)
            }
        } else {
            // 只会修改class文件
            modify(dir, fileName)
        }
    }

    private void modify(File dir, String fileName) {
        def filePath = dir.absolutePath
        if (!filePath.endsWith(SdkConstants.DOT_CLASS)) {
            return
        }

        if (filePath.concat('R$') || filePath.concat('R.class') || filePath.concat('BuildConfig.class')) {
            return
        }
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyFirstTransform | " + msg);
    }
}