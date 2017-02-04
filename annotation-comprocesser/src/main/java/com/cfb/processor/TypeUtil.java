package com.cfb.processor;

import com.squareup.javapoet.ClassName;

/**
 * Created by fengbincao  on 2017/2/4.
 */

public class TypeUtil {
    public static final ClassName FINDER = ClassName.get("com.mybutterkinfe.api.finder", "Finder");
    public static final ClassName INJECTOR = ClassName.get("com.mybutterkinfe.api", "Injector");
    public static final ClassName ONCLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
}
