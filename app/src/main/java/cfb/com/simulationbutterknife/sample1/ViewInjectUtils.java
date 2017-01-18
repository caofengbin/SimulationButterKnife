package cfb.com.simulationbutterknife.sample1;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fengbincao on 2017/1/18.
 */

public class ViewInjectUtils {

    public static void bind(Activity activity) {
        injectContentView(activity);
        injectView(activity);
        injectOnClick(activity);
    }

    // 处理@ContentView注解
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null) {
            //如果这个activity上存在@ContentView注解，就取出这个注解对应的value值
            int layoutId = contentView.value();
            //也就是布局文件的id。
            try {
                Method setViewMethod = clazz.getMethod("setContentView",int.class);
                setViewMethod.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 处理@BindView注解
    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 获得activity的所有成员变量
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            //获得每个成员变量上面的BindView注解
            BindView bindView = field.getAnnotation(BindView.class);
            if(bindView != null) {
                // 得到相应的控件id
                int viewId = bindView.value();
                View view = activity.findViewById(viewId);
                try {
                    // 允许访问私有字段
                    field.setAccessible(true);
                    // 将获得指定id的View设置给使用了@BindView的field。
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 处理@OnClick注解
    private static void injectOnClick(final Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(final Method tempMethod : methods) {
            // 获得使用了@OnClick注解的方法
            OnClick click = tempMethod.getAnnotation(OnClick.class);
            if(click != null) {
                int[] viewIds = click.value();
                tempMethod.setAccessible(true);

                Object myListener = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                        new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                // 调用带有onClick注解的method方法
                                return tempMethod.invoke(activity, args);
                            }
                        });

                try {
                    for(int id : viewIds) {
                        View tempView = activity.findViewById(id);
                        Method setOnClickerListener = tempView.getClass().getMethod("setOnClickListener",
                                View.OnClickListener.class);
                        // 通过动态代理调用自己设置的myListener
                        setOnClickerListener.invoke(tempView, myListener);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
