package com.mybutterkinfe.api.finder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by fengbincao on 2017/2/4.
 */

public class MyFinder implements Finder {

    @Override
    public Context getContext(Object source) {
        return (Activity) source;
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity) source).findViewById(id);
    }
}