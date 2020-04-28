package com.mjiayou.myplugin

import com.android.SdkConstants
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.CtClass
import javassist.CtMethod
import com.mjiayou.myvisitor.MyVisitor
import groovy.io.FileType
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils

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
        return TransformManager.SCOPE_FULL_PROJECT
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
        log("111")

        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }

        // 拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        log("transformInputs | transformInputs.size() = " + transformInputs.size() + " | --------------------------------")
        for (int i = 0; i < transformInputs.size(); i++) {
            TransformInput transformInput = transformInputs[i]

            // 遍历directoryInputs
            // 即文件夹中的class文件，directoryInputs代表着以源码方式参与项目编译的class文件
            // 比如我们手写的类，以及R.class、BuildConfig.class以及MainActivity.class等
            Collection<DirectoryInput> directoryInputs = transformInput.directoryInputs
            log("transformInputs | directoryInputs | directoryInputs.size() = " + directoryInputs.size() + " | --------------------------------")
            for (int j = 0; j < directoryInputs.size(); j++) {
                DirectoryInput directoryInput = directoryInputs[j]
                // 信息
                def directoryInputName = directoryInput.name // android.local.jars:httpmime-4.1.3.jar:00c4d7d6ff94005ea19e3ae22cb63ea9b0d65ea1
                def directoryInputFile = directoryInput.file // /Users/xin/Documents/Projects/TreCore/TreCoreLib/libs/httpmime-4.1.3.jar
                def directoryInputFileName = directoryInputFile.name // httpmime-4.1.3.jar
                def directoryInputFilePath = directoryInputFile.absolutePath // /Users/xin/Documents/Projects/TreCore/TreCoreLib/libs/httpmime-4.1.3.jar
                log("transformInputs | directoryInputs | j = " + j + " | directoryInputName = " + directoryInputName + " | directoryInputFile = " + directoryInputFile)
                log("transformInputs | directoryInputs | j = " + j + " | directoryInputFileName = " + directoryInputFileName + " | directoryInputFilePath = " + directoryInputFilePath)

                // AOP - ASM
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

                // AOP - javassist
                MyInsert myInsert = new MyInsert()
                myInsert.insertCode(directoryInputFilePath, project)

                // 输出
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInputFile, dest)
            }

            // 遍历jarInputs
            // 代表着jar里的class文件
            Collection<JarInput> jarInputs = transformInput.jarInputs
            log("transformInputs | jarInputs | jarInputs.size() = " + jarInputs.size() + " | --------------------------------")
            for (int k = 0; k < jarInputs.size(); k++) {
                // 信息
                JarInput jarInput = jarInputs[k]
                def jarInputName = jarInput.name // 760d89b2c2d9beb87359daa3f0ee75f0fd5622cb
                def jarInputFile = jarInput.file // /Users/xin/Documents/Projects/TreCore/app/build/intermediates/javac/officialDebug/classes
                def jarInputFileName = jarInputFile.name // classes
                def jarInputFilePath = jarInputFile.absolutePath // /Users/xin/Documents/Projects/TreCore/app/build/intermediates/javac/officialDebug/classes
                log("transformInputs | jarInputs | k = " + k + " | jarInputName = " + jarInputName + " | jarInputFile = " + jarInputFile)
                log("transformInputs | jarInputs | k = " + k + " | jarInputFileName = " + jarInputFileName + " | jarInputFilePath = " + jarInputFilePath)

                // 输出
                def dest = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInputFile, dest)
            }
        }

        log("******************************** transform-end ********************************")
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyFirstTransform | " + msg);
    }
}