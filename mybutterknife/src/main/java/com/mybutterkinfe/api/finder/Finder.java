package com.mybutterkinfe.api.finder;

import android.content.Context;
import android.view.View;

/**
 * Created by fengbincao on 2017/2/4.
 */

public interface Finder {
    /**
     * 根据source获取Context
     * @param source
     * @return
     */
    Context getContext(Object source);

    /**
     * 根据id找控件
     * @param source
     * @param id
     * @return
     */
    View findView(Object source, int id);
}
