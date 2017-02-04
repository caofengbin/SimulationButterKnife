package com.mybutterkinfe.api;


import com.mybutterkinfe.api.finder.Finder;

/**
 * Created by fengbincao on 2017/2/4.
 */

public interface Injector<T>{
    /**
     * @param host   目标
     * @param source 来源
     * @param finder
     */
    void inject(T host, Object source, Finder finder);
}
