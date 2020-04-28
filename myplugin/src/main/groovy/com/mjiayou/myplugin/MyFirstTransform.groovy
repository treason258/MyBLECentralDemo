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

public class MyFirstTransform extends Transform {

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

        log("******************************** transform-end ********************************")
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyFirstTransform | " + msg);
    }
}