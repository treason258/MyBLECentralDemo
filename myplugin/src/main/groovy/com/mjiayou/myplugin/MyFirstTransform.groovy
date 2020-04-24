package com.mjiayou.myplugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.mjiayou.myvisitor.MyVisitor
import groovy.io.FileType
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

//import javassist.ClassPool
//import com.android.SdkConstants


public class MyFirstTransform extends Transform {

//    // 字节码池
//    def pool = ClassPool.default;

    // 工程，有所有class的路径
    Project project

    MyFirstTransform(Project project) {
        this.project = project
    }

    /**
     * 定义Task名字
     */
    @Override
    String getName() {
        return "treason"
    }

    /**
     * 定义搜索类型
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 定义搜索范围
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    /**
     * 是否需要增量更新
     */
    @Override
    boolean isIncremental() {
        return false
    }

    // 修改字节码
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        log("******************************** transform-start ********************************")
        log("999")

        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }

        // 拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        log("-------------------------------- | transformInputs.size() = " + transformInputs.size())
        for (int i = 0; i < transformInputs.size(); i++) {
            TransformInput transformInput = transformInputs[i]

            // 遍历directoryInputs
            // 即文件夹中的class文件，directoryInputs代表着以源码方式参与项目编译的class文件
            // 比如我们手写的类，以及R.class、BuildConfig.class以及MainActivity.class等
            Collection<DirectoryInput> directoryInputs = transformInput.directoryInputs
            log("-------------------------------- | directoryInputs.size() = " + directoryInputs.size())
            for (int j = 0; j < directoryInputs.size(); j++) {
                DirectoryInput directoryInput = directoryInputs[j]
                def directoryInputName = directoryInput.name
                def directoryInputFile = directoryInput.file
                log("j = " + j + " | directoryInputName = " + directoryInputName + " | directoryInputFile = " + directoryInputFile.absolutePath)

                directoryInputFile.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                    File file ->
                        def fileName = file.name
                        def filePath = file.absolutePath
                        // log("fileName = " + fileName + " | filePath = " + filePath)
                        if (fileName.endsWith(".class") && !fileName.startsWith("R\$") && !fileName.equals("R.class") && !fileName.equals("BuildConfig.class")) {
                            log("--------")
                            log("fileName = " + fileName + " | filePath = " + filePath)

                            // 解析class
                            ClassReader classReader = new ClassReader(file.bytes)
                            // 写入class
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            // 访问class
                            ClassVisitor classVisitor = new MyVisitor(classWriter)
                            // 调用class里的方法
                            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)

                            byte[] bytes = classWriter.toByteArray()
                            FileOutputStream fileOutputStream = new FileOutputStream(file.path);
                            fileOutputStream.write(bytes)
                            fileOutputStream.close()
                        }
                }
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInputFile, dest)
            }

            // 遍历jarInputs
            // 代表着jar里的class文件
            Collection<JarInput> jarInputs = transformInput.jarInputs
            log("-------------------------------- | jarInputs.size() = " + jarInputs.size())
            for (int k = 0; k < jarInputs.size(); k++) {
                JarInput jarInput = jarInputs[k]
                def jarInputName = jarInput.name
                def jarInputFile = jarInput.file
                log("k = " + k + " | jarInputName = " + jarInputName + " | jarInputFile = " + jarInputFile.absolutePath)
            }
        }

//        transformInputs.each {
//            TransformInput transformInput ->
//                transformInput.directoryInputs.each {
//                    DirectoryInput directoryInput ->
//                        File dir = directoryInput.file
//                        if (dir) {
//                            dir.traverse(types: FileType.FILES, nameFilter: ~/.*\.class/) {
//                                File file ->
//                                    def name = file.name
//                                    if (name.endsWith(".class") && !name.startsWith("R\$") && !"R.class".equals(name) && !"BuildConfig.class".equals(name))
//                                        log("class = " + file.name)
//                            }
//                        }
//
//                        def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
//                        FileUtils.copyDirectory(directoryInput.file, dest)
//                }
//        }


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

//    private void findTarget(File dir, String fileName) {
////        log("fileName = " + fileName);
//        // 找到.class结尾的文件
//        if (dir.isDirectory()) {
//            dir.listFiles().each {
//                findTarget(it, fileName)
//            }
//        } else {
//            // 只会修改class文件
//            modify(dir, fileName)
//        }
//    }
//
//    private void modify(File dir, String fileName) {
//        def filePath = dir.absolutePath
//        if (!filePath.endsWith(SdkConstants.DOT_CLASS)) {
//            return
//        }
//
//        if (filePath.concat('R$') || filePath.concat('R.class') || filePath.concat('BuildConfig.class')) {
//            return
//        }
//    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyFirstTransform | " + msg);
    }
}