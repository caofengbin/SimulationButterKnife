package com.cfb.processor.model;

import com.cfb.processor.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
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

    // 被@ContentView注解标记的变量
    public BindLayout mBindLayout;

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

    // 添加BindLayout
    public void setBindLayoutField(BindLayout field) {
        mBindLayout = field;
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
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)                  // 添加方法描述为public
                .addAnnotation(Override.class)                  // 添加方法注解
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL)  // 添加方法的参数
                .addParameter(TypeName.OBJECT, "source")                                     // 添加方法的参数
                .addParameter(TypeUtil.FINDER, "finder");                                    // 添加方法的参数

        // 生成绑定布局文件相关的代码
        if(mBindLayout != null) {
            // 如果 layoutId 不为 0 ，那说明有绑定，添加一句 setContentView
            // 要注意的是，这句要比 view 注入在前面
            if(mBindLayout.getLayoutId() != 0) {
                methodBuilder.addStatement("host.setContentView($L)", mBindLayout.getLayoutId());
            }
        }

        // 生成View注入相关的代码
        for(BindViewField field : mBindViewFieldList) {
            // 添加一行
            methodBuilder.addStatement("host.$N=($T)finder.findView(source,$L)", field.getFieldName()
                    , ClassName.get(field.getFieldType()), field.getResId());
        }

        // 添加声明的Listener
        if(mOnClickMethodList.size() > 0) {
            methodBuilder.addStatement("$T listener", TypeUtil.ONCLICK_LISTENER);
        }

        for(OnClickMethod method : mOnClickMethodList) {
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ONCLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N()", method.getMethodName())
                            .build())
                    .build();
            methodBuilder.addStatement("listener = $L ", listener);
            for (int id : method.ids) {
                methodBuilder.addStatement("finder.findView(source,$L).setOnClickListener(listener)", id);
            }
        }

        String packageName = getPackageName(mClassElement);
        String className = getClassName(mClassElement,packageName);
        ClassName bindClassName = ClassName.get(packageName,className);

        // 构建类
        TypeSpec finderClass = TypeSpec.classBuilder(bindClassName.simpleName()+ "$$Injector")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECTOR, TypeName.get(mClassElement.asType())))
                .addMethod(methodBuilder.build())
                .build();
        return JavaFile.builder(packageName, finderClass).build();
    }
}
