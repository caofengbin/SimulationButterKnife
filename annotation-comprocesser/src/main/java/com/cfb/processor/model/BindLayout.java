package com.cfb.processor.model;

import com.annotation.ContentView;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static java.lang.reflect.Modifier.PRIVATE;

/**
 * ButterKnifeAnnotatedClass的成员变量
 * 被@ContentView注解标记的字段的模型类
 * Created by fegnbincao on 2017/2/4.
 */

public class BindLayout {

    private TypeElement mTypeElement;

    private int mLayoutId;

    public BindLayout(Element element) throws IllegalArgumentException {
//        if(element.getKind() != ElementKind.CLASS) {
//            // 判断使用该注解的元素是否是Field
//            throw new IllegalArgumentException(String.format("Only Activity class can be annotated with @%s",
//                    ContentView.class.getSimpleName()));
//        }
        mTypeElement = (TypeElement)element;
        Set<Modifier> modifiers = element.getModifiers();
        // 只有 private 不可以访问到，static 类型不影响，这也是与其他注解不同的地方
        if (modifiers.contains(PRIVATE)) {
            return;
        }


        // 同样的，对于 android 开头的包内的类不予支持
        String qualifiedName = mTypeElement.getSimpleName().toString();
        if (qualifiedName.startsWith("android.")) {
            return;
        }

        // 同样的，对于 java 开头的包内的类不予支持
        if (qualifiedName.startsWith("java.")) {
            return;
        }

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
