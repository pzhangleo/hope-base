package com.being.base.processor;

import com.being.base.annotation.ParamKey;
import com.being.base.annotation.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SuppressWarnings("unused")
@SupportedAnnotationTypes({"com.being.base.annotation.ParamKey", "com.being.base.annotation.Request"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RequestProcessor extends AbstractProcessor{

    public static final String SUFFIX = "Api";

    public static final String PREFIX = "";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            if (annotation.getSimpleName().equals(Request.class.getSimpleName())) {
                try {
                    return processRequest(annotation, roundEnv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;

        }
        return true;
    }

    private boolean processRequest(TypeElement t, RoundEnvironment roundEnv) throws IOException {
        for (Element e : roundEnv.getElementsAnnotatedWith(t)) {
            printLog(e);
            Request request = e.getAnnotation(Request.class);
            // 获取元素名并将其首字母大写
            String name = e.getSimpleName().toString();
            char c = Character.toUpperCase(name.charAt(0));
            name = String.valueOf(c+name.substring(1));

            // 包裹注解元素的元素, 也就是其父元素, 比如注解了成员变量或者成员函数, 其上层就是该类
            Element enclosingElement = e.getEnclosingElement();
            // 获取父元素的全类名, 用来生成包名
            String enclosingQualifiedName;
            if(enclosingElement instanceof PackageElement){
                enclosingQualifiedName = ((PackageElement)enclosingElement).getQualifiedName().toString();
            }else {
                enclosingQualifiedName = ((TypeElement)enclosingElement).getQualifiedName().toString();
            }
            try {
                // 生成的包名
                String genaratePackageName = enclosingQualifiedName.substring(0, enclosingQualifiedName.lastIndexOf('.'));
                // 生成的类名
                String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;

                // 创建Java文件
                JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
                Messager messager = processingEnv.getMessager();
                // 在控制台输出文件路径
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());
                Writer w = f.openWriter();
                try {
                    PrintWriter pw = new PrintWriter(w);
                    pw.println("package " + genaratePackageName + ";");
                    pw.println("\n");
                    pw.println("import com.being.base.http.BaseRequestParams;\n");
                    pw.println("import " + request.response().getCanonicalName() + ";\n");
                    pw.println("\npublic class " + genarateClassName + "extends " + request.superClass() + " { ");
                    pw.println("\n    /** generate class */");
                    pw.println("    public static void " + request.method().toString() + name + "(" + name + " p, ResponseCallback<" + request.response().getSimpleName() + ">" + " callback) {");
                    pw.println("        // 注解的父元素: " + enclosingElement.toString() + "\n");
                    pw.println("        BaseRequestParams params = new BaseRequestParams(" + request.command() + ");\n");
                    for (Element element : e.getEnclosedElements()) {
                        if (element.getKind() == ElementKind.FIELD) {
                            String n = element.getSimpleName().toString();
                            Annotation a = element.getAnnotation(ParamKey.class);
                            if (a != null) {
                                n = ((ParamKey) a).value();
                            }
                    pw.println("        params.put(" + element.getSimpleName() + ", p." + n + ")");
                        }
                    }
                    String method = request.method().name().toLowerCase();
                    pw.println("        " + method + "(params, callback)");
                    pw.println("}");
                    pw.flush();
                } finally {
                    w.close();
                }
            } catch (IOException x) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        x.toString());
            }
        }
        return true;
    }

    private void printLog(Element e) {
        // 准备在gradle的控制台打印信息
        Messager messager = processingEnv.getMessager();
        // 打印
        messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
        messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getSimpleName());
        messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getEnclosingElement().toString());
    }
}
