package com.cfb.processor.model;

import com.annotation.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * ButterKnifeAnnotatedClass的成员变量
 * 被@BindView注解标记的字段的模型类
 * Created by fengbincao on 2017/1/19.
 */

public class BindViewField {

    private VariableElement mFieldElement;

    private int mResId;

    public BindViewField(Element element) throws IllegalArgumentException {
        if(element.getKind() != ElementKind.FIELD) {
            // 判断使用该注解的元素是否是Field
            throw new IllegalArgumentException(String.format("Only field can be annotated with @%s",
                    BindView.class.getSimpleName()));
        }
        mFieldElement = (VariableElement)element;
        // 获取注解和值
        BindView bindView = mFieldElement.getAnnotation(BindView.class);
        mResId = bindView.value();
        if(mResId < 0) {
            throw new IllegalArgumentException(String.format("value() in %s for field %s is not valid",
                    BindView.class.getSimpleName(), mFieldElement.getSimpleName()));
        }
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public int getResId() {
        return mResId;
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }
}
