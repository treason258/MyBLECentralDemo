package com.mjiayou.myannotationcompiler;

import com.google.auto.service.AutoService;
import com.mjiayou.myannotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * 注解处理器
 */
@AutoService(Processor.class) // 进行注册
public class AnnotationCompiler extends AbstractProcessor {

    private Filer mFiler; // 生成文件的对象

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    /**
     * 声明要处理的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的java原版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 核心-当注解处理器检索完代码之后会回调
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        log("******************************** AnnotationCompiler-process-start ********************************");
//        log("processingEnv = " + processingEnv);
//        log("set = " + set.toString());
//        log("roundEnvironment = " + roundEnvironment);

        // 获取到所有依赖了这个注解处理器的模块中用到了BindPath注解的下面的内容：Element
        // TypeElement-类
        // VariableElement-成员变量
        // ExecutableElement-方法
        Set<? extends Element> activitySet = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        log("activitySet = " + activitySet.toString());

        Map<String, String> map = new HashMap<>();
        for (Element activity : activitySet) {
            if (activity instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) activity;
                // 得到这个类上面的注解，以及它里面的类
                String key = typeElement.getAnnotation(BindPath.class).value();
                // 获取这个类的包名和类名
                String activityName = typeElement.getQualifiedName().toString();
                map.put(key, activityName + ".class");
            }
        }
        log("map = " + map.toString());

//        if (true) {
//            Writer writer = null;
//            try {
//                String activityName = "TCRouterUtils_" + System.currentTimeMillis();
//                JavaFileObject sourceFile = mFiler.createSourceFile("com.mjiayou.myannotation." + activityName);
//                writer = sourceFile.openWriter();
//                writer.write("// Generated code from TreCore111. Do not modify!\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (writer != null) {
//                    try {
//                        writer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return false;
//        }

        // 写代码
        if (map.size() > 0) {
            Writer writer = null;
            // 创建一个类名
            String activityName = "TCRouterUtils_" + System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = mFiler.createSourceFile("com.mjiayou.myannotation." + activityName);
                writer = sourceFile.openWriter();
                writer.write("// Generated code from TreCore. Do not modify!\n");
                writer.write("package com.mjiayou.myannotation;\n" +
                        "\n" +
                        "import com.mjiayou.trerouter.IRouter;\n" +
                        "import com.mjiayou.trerouter.TCRouter;\n" +
                        "\n" +
                        "public class " + activityName + " implements IRouter {\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public void putActivity() {");

                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String className = map.get(key);
                    writer.write("TCRouter.get().addActivity(\"" + key + "\", " + className + ");");
                }
                writer.write("    }\n" +
                        "}\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    private void log(String msg) {
        System.out.println("matengfei888 | AnnotationCompiler | " + msg);
    }
}
