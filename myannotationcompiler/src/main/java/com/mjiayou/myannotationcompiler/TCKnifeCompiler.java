package com.mjiayou.myannotationcompiler;

import com.google.auto.service.AutoService;
import com.mjiayou.myannotation.TCBindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
//@SupportedAnnotationTypes({"com.mjiayou.myannotation.TCBindView"})
//@SupportedAnnotationTypes({TCBindView.class.getCanonicalName()})
//@SupportedSourceVersion(processingEnv.getSourceVersion())
public class TCKnifeCompiler extends AbstractProcessor {

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
        types.add(TCBindView.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的java原版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(TCBindView.class);

        Map<String, List<VariableElement>> map = new HashMap<>();
        // 遍历所有用到的了BindView的控件，将所有的控件和类都一一对应起来
        for (Element element : elements) {
            if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                // 通过这个成员变量的节点，获取它的类名
                String className = variableElement.getEnclosingElement().getSimpleName().toString();
                List<VariableElement> list = map.get(className);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(className, list);
                }
                list.add(variableElement);
            }
        }

        // 写文件
        Writer writer = null;
        for (String key : map.keySet()) {
            // 获取到类名
            String activityName = key + "_TCViewBinding";
            // 获取到这个类下面所有的控件
            List<VariableElement> variableElements = map.get(key);
            // 获取到包名
            String packageName = processingEnv.getElementUtils().getPackageOf(variableElements.get(0).getEnclosingElement()).toString();
            try {
                // 编辑代码
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("// Generated code from TreCore. Do not modify! 333").append("\n");
                stringBuffer.append("\n");
                stringBuffer.append("package " + packageName + ";").append("\n");
                stringBuffer.append("\n");
                stringBuffer.append("import com.mjiayou.trerouter.IViewBinder;").append("\n");
                stringBuffer.append("\n");
                stringBuffer.append("public class " + activityName + " implements IViewBinder<" + packageName + "." + key + "> {").append("\n");
                stringBuffer.append("\n");
                stringBuffer.append("\t").append("public void bind(" + packageName + "." + key + " target) {").append("\n");
                for (VariableElement variableElement : variableElements) {
                    // 得到控件的名字
                    String varibleName = variableElement.getSimpleName().toString();
                    // 获取到控件的ID
                    int resId = variableElement.getAnnotation(TCBindView.class).value();
                    // 获取控件的类型
                    TypeMirror typeMirror = variableElement.asType();
                    // 编辑代码
                    stringBuffer.append("\t").append("\t").append("target." + varibleName + " = (" + typeMirror + ") target.findViewById(" + resId + ");").append("\n");
                }
                stringBuffer.append("\t").append("}").append("\n");
                stringBuffer.append("}").append("\n");

                // 写代码
                JavaFileObject sourceFile = mFiler.createSourceFile(packageName + "." + activityName);
                writer = sourceFile.openWriter();
                writer.write(stringBuffer.toString());
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
        System.out.println("matengfei888 | TCKnifeCompiler | " + msg);
    }
}
