package com.cfb.processor.model;

import com.squareup.javapoet.JavaFile;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 用于保存被注解类的数据的核心--ButterKnifeAnnotatedClass
 * 可以理解为，我们在解析XML，
 * 解析Json的时候数据解析完之后需要以对象的形式表示出来，
 * Created by fengbincao on 2017/1/19.
 */

public class ButterKnifeAnnotatedClass {

    // 类名
    public TypeElement mClassElement;

    // 元素辅助类
    public Elements mElementsUtils;

    // 成员变量集合，被@BindView注解标记的变量集合
    public List<BindViewField> mBindViewFieldList;

    // 成员变量集合，被@OnClick注解标记的变量集合
    public List<OnClickMethod> mOnClickMethodList;

    public ButterKnifeAnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementsUtils = elementUtils;
        this.mBindViewFieldList = new ArrayList<>();
        this.mOnClickMethodList = new ArrayList<>();
    }

    // 获取当前类的全名
    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    // 添加BindViewField
    public void addBindViewField(BindViewField field) {
        mBindViewFieldList.add(field);
    }

    // 添加OnClickMethod
    public void addOnClickMethod(OnClickMethod method) {
        mOnClickMethodList.add(method);
    }

    // 包名
    public String getPackageName(TypeElement type) {
        return mElementsUtils.getPackageOf(type).getQualifiedName().toString();
    }

    // 类名
    public String getClassName(TypeElement type,String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    // 输出java
    // 生成代码的关键部分
    public JavaFile generateFinder() {
        //构建方法
    }
}
