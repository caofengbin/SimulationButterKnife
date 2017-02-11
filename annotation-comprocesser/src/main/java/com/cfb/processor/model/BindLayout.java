package com.cfb.processor.model;

import com.annotation.ContentView;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * ButterKnifeAnnotatedClass的成员变量
 * 被@ContentView注解标记的字段的模型类
 * Created by fegnbincao on 2017/2/4.
 */

public class BindLayout {

    private TypeElement mTypeElement;

    private int mLayoutId;

    public BindLayout(Element element) throws IllegalArgumentException {
        mTypeElement = (TypeElement)element;

        // 获取注解和值
        ContentView contentView = mTypeElement.getAnnotation(ContentView.class);
        mLayoutId = contentView.value();
        if(mLayoutId < 0) {
            throw new IllegalArgumentException(String.format("value() in %s for field %s is not valid",
                    ContentView.class.getSimpleName(), mTypeElement.getSimpleName()));
        }
    }

    // 获取layout的id，即注解的value值
    public int getLayoutId() {
        return mLayoutId;
    }
}
