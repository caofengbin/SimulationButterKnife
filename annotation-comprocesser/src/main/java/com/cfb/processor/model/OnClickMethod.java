package com.cfb.processor.model;

import com.annotation.OnClick;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * ButterKnifeAnnotatedClass的成员变量
 * 被@OnClick注解标记的字段的模型类
 * Created by fengbincao on 2017/1/19.
 */

public class OnClickMethod {

    private Name mMethodName;

    public int[] ids;

    public OnClickMethod(Element element) {
        if(element.getKind() != ElementKind.METHOD) {
            // 判断使用该注解的元素是否是METHOD
            throw new IllegalArgumentException(String.format("Only method can be annotated with @%s",
                    OnClick.class.getSimpleName()));
        }
        ExecutableElement methodElement = (ExecutableElement)element;
        mMethodName = methodElement.getSimpleName();
        ids = methodElement.getAnnotation(OnClick.class).value();
        if(ids == null) {
            throw new IllegalArgumentException(String.format("Must set valid ids for @%s", OnClick.class.getSimpleName()));
        } else {
            for(int id : ids) {
                if(id < 0) {
                    throw new IllegalArgumentException(String.format("Must set valid ids for @%s", OnClick.class.getSimpleName()));
                }
            }
        }

        List<? extends VariableElement> parameters = methodElement.getParameters();
        if(parameters.size() > 0) {
            throw new IllegalArgumentException(String.format("The method annotated with @%s must have no parameters", OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return mMethodName;
    }
}
