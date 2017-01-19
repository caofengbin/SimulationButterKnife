package com.cfb.processor;

import com.annotation.BindView;
import com.annotation.OnClick;
import com.cfb.processor.model.BindViewField;
import com.cfb.processor.model.ButterKnifeAnnotatedClass;
import com.cfb.processor.model.OnClickMethod;
import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 自定义的注解处理器
 * Created by fengbincao on 2017/1/19.
 */

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    // 文件相关辅助类
    private Filer mFiler;

    // 元素相关辅助类
    private Elements mElementUtils;

    // 日志相关辅助类
    private Messager mMessager;

    // 解析的目标注解集合
    private Map<String,ButterKnifeAnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    // 返回值是一个字符串的集合，包含本处理器想要处理的注解类型的合法全称
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        // 对于大部分class而言，getCanonicalName和getName这两个方法没有什么不同的，
        // 但是对于array或内部类等就显示出来了。
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        return types;
    }

    // 用来指定使用的Java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mAnnotatedClassMap.clear();
        try {
            processBindView(roundEnvironment);
            processOnClick(roundEnvironment);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true;
        }
        try {
            for(ButterKnifeAnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
                info("generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(mFiler);
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("Generate file failed,reason:%s", e.getMessage());
        }
        return true;
    }

    // 处理@BindView相关的注解
    private void processBindView(RoundEnvironment roundEnvironment) {
        for(Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            ButterKnifeAnnotatedClass annotatedClass = getButterKnifeAnnotatedClass(element);
            BindViewField bindViewField = new BindViewField(element);
            annotatedClass.addBindViewField(bindViewField);
        }
    }

    // 处理@OnClick相关的注解
    private void processOnClick(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(OnClick.class)) {
            ButterKnifeAnnotatedClass annotatedClass = getButterKnifeAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addOnClickMethod(method);
        }
    }

    private ButterKnifeAnnotatedClass getButterKnifeAnnotatedClass(Element element) {
        TypeElement encloseElement = (TypeElement) element.getEnclosingElement();
        String fullName = encloseElement.getQualifiedName().toString();
        // 检查是否有缓存
        ButterKnifeAnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullName);
        if(annotatedClass == null) {
            annotatedClass = new ButterKnifeAnnotatedClass(encloseElement,mElementUtils);
            mAnnotatedClassMap.put(fullName,annotatedClass);
        }
        return annotatedClass;
    }

    // 写错误日志
    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    // 打印日志
    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
