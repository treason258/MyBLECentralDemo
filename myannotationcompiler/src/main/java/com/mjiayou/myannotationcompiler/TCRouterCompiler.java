package com.mjiayou.myannotationcompiler;

import com.google.auto.service.AutoService;
import com.mjiayou.myannotation.TCBindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
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
public class TCRouterCompiler extends AbstractProcessor {

    private Filer mFiler; // 生成文件的对象
    public static final String TC_ROUTER_UTILS_PACKAGE_NAME = "com.mjiayou.myannotation";
    public static String TC_ROUTER_UTILS_CLASS_NAME = "TCRouterUtils";

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
        types.add(TCBindPath.class.getCanonicalName());
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
        // 获取到所有依赖了这个注解处理器的模块中用到了BindPath注解的下面的内容：Element
        // TypeElement-类
        // VariableElement-成员变量
        // ExecutableElement-方法
        Set<? extends Element> activitySet = roundEnvironment.getElementsAnnotatedWith(TCBindPath.class);
        if (activitySet == null || activitySet.size() == 0) {
            return false;
        }

        Map<String, String> map = new HashMap<>();
        for (Element activity : activitySet) {
            if (activity instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) activity;
                // 得到这个类上面的注解，以及它里面的类
                String key = typeElement.getAnnotation(TCBindPath.class).value();
                // 获取这个类的包名和类名
                String clazz = typeElement.getQualifiedName().toString() + ".class";
                map.put(key, clazz);
            }
        }
        log("******************************** process-start ********************************");
        log("map = " + map.toString());
        log("******************************** process-end ********************************");

        if (map.size() > 0) {
            Writer writer = null;
            try {
                // 定义类名
                String activityName = TC_ROUTER_UTILS_CLASS_NAME + "_" + System.currentTimeMillis();
                String activityPath = TC_ROUTER_UTILS_PACKAGE_NAME + "." + activityName;

                // 编辑代码
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("// Generated code from TreCore. Do not modify! 333").append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("package com.mjiayou.myannotation;").append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("import com.mjiayou.treannotation.ITCRouter;").append("\n");
                stringBuilder.append("import com.mjiayou.treannotation.TCRouter;").append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("public class " + activityName + " implements ITCRouter {").append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("\t").append("@Override").append("\n");
                stringBuilder.append("\t").append("public void putActivity() {").append("\n");
                for (String key : map.keySet()) {
                    String clazz = map.get(key);
                    stringBuilder.append("\t").append("\t").append("TCRouter.get().addActivity(\"" + key + "\", " + clazz + ");").append("\n");
                }
                stringBuilder.append("\t").append("}").append("\n");
                stringBuilder.append("}").append("\n");

                // 写代码
                JavaFileObject sourceFile = mFiler.createSourceFile(activityPath);
                writer = sourceFile.openWriter();
                writer.write(stringBuilder.toString());
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
        System.out.println("matengfei888 | TCRouterCompiler | " + msg);
    }
}
