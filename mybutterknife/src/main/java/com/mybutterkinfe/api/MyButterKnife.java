package com.mybutterkinfe.api;

import android.app.Activity;

import com.mybutterkinfe.api.finder.ActivityFinder;
import com.mybutterkinfe.api.finder.Finder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengbincao on 2017/1/19.
 */

public class MyButterKnife {

    private static final ActivityFinder ACTIVITY_FINDER = new ActivityFinder();

    private static Map<String, Injector> FINDER_MAP = new HashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity, ACTIVITY_FINDER);
    }

    public static void bind(Object host, Object source, Finder finder) {
        String className = host.getClass().getName();
        try {
            Injector injector = FINDER_MAP.get(className);
            if (injector == null) {
                Class<?> finderClass = Class.forName(className + "$$Injector");
                injector = (Injector) finderClass.newInstance();
                FINDER_MAP.put(className, injector);
            }
            injector.inject(host, source, finder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
