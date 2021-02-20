package com.pa.hook.framehook;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.pa.hook.framehook.instrumentation.InstrumentationProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by yuan on 2018/12/7.
 */

public class HookHelper {

    public static void hookAms() {
        Object defaultSingleton = null;
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                //ActivityManager.getService()方法在frame中是隐藏的，需要通过反射获取IActivityManager对象
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
                Field field = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
                field.setAccessible(true);
                //获取ActivityManager内部的一个静态对象。
                defaultSingleton = field.get(null);//IActivityManagerSingleton是静态的

                Class<?> singletonClazz = Class.forName("android.util.Singleton");//
                Field mInstanceFields = singletonClazz.getDeclaredField("mInstance");
                mInstanceFields.setAccessible(true);
                //获取IActivityManagerSingleton对象里的mInstance变量，即IActivityManager对象
                Object iActivityManager = mInstanceFields.get(defaultSingleton);
                //创建自己的IActivityManager代理类，用于拦截startActivity方法
                Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{iActivityManagerClass},
                        new IActivityManagerProxy(iActivityManager));
                //将IActivityManagerSingleton里的mInstance替换成proxy，即IActivityManager替换成proxy
                mInstanceFields.set(defaultSingleton, proxy);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hookHandler() throws Exception {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        //获取ActivityThread类的sCurrentActivityThread静态对象。即ActivityThread对象
        Field field = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
        field.setAccessible(true);
        Object currentActivityThread = field.get(null);
        Field hField = activityThreadClazz.getDeclaredField("mH");
        hField.setAccessible(true);
        Handler mH = (Handler) hField.get(currentActivityThread);
        //重新设置Handler的mCallback，mCallback只有Handler构造方法创建，因此也需要用反射赋值
        Field mCallBack = Handler.class.getDeclaredField("mCallback");
        mCallBack.setAccessible(true);
        mCallBack.set(mH, new HCallBack(mH));
    }

    public static void hookInstrumentation(Context context) {

        try {
            Class<?> activityClass =  Class.forName("android.app.ContextImpl");
            Field field = activityClass.getDeclaredField("mMainThread");
            field.setAccessible(true);
            //获取ActivityThread对象
            Object object = field.get(context);
            Class<?> activitythreadClass = Class.forName("android.app.ActivityThread");
            Field instrumentation = activitythreadClass.getDeclaredField("mInstrumentation");
            instrumentation.setAccessible(true);
            instrumentation.set(object, new InstrumentationProxy(context.getPackageManager(), (Instrumentation) instrumentation.get(object)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
