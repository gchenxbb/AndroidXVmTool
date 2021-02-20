package com.pa.hook.proxy;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//代理Service，用于分发
public class ProxyService extends Service {
    private final String TAG = "ProxyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ProxyService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "ProxyService onStartCommand");
        if (intent == null || !intent.hasExtra("target_service")) {
            return START_STICKY;
        }
        String serviceName = intent.getStringExtra("target_service");
        if (serviceName == null) {
            return START_STICKY;
        }
        //分发
        Service targetService;
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method getApplicationThreadMethod = activityThreadClass.getDeclaredMethod("getApplicationThread");
            getApplicationThreadMethod.setAccessible(true);
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            //获取ActivityThread对象
            Object activityThread = activityThreadField.get(null);
            //执行getApplicationThread方法。获取 ApplicationThread对象。
            Object applicationThread = getApplicationThreadMethod.invoke(activityThread);

            /*****获取IBinder token。*****/
            Class<?> interfaceClass = Class.forName("android.os.IInterface");
            Method asBinder = interfaceClass.getDeclaredMethod("asBinder");
            asBinder.setAccessible(true);
            Object token = asBinder.invoke(applicationThread);

            /*****获取activityManager。*****/
            Object defaultSingleton = null;
            if (Build.VERSION.SDK_INT >= 26) {
                //ActivityManager.getService()方法在frame中是隐藏的，需要通过反射获取IActivityManager对象
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
                Field field = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
                field.setAccessible(true);
                //获取ActivityManager内部的一个静态对象。
                defaultSingleton = field.get(null);//IActivityManagerSingleton是静态的
            } else {
                //针对26以下的
            }
            Class<?> singletonClazz = Class.forName("android.util.Singleton");//
            Field mInstanceFields = singletonClazz.getDeclaredField("mInstance");
            mInstanceFields.setAccessible(true);
            //获取IActivityManagerSingleton对象里的mInstance变量，即IActivityManager对象
            Object iActivityManager = mInstanceFields.get(defaultSingleton);

            //创建目标Service实例
            targetService = (Service) Class.forName(serviceName).newInstance();
            //目标Service的attach方法。主要参数如下，因此上面代码主要就是获取这些参数
            //Context ,ActivityThread , String className, IBinder token,Application , Object activityManager
            Class<?> serviceClass = Class.forName("android.app.Service");
            Method attachMethod = serviceClass.getDeclaredMethod("attach", Context.class, activityThreadClass, String.class,
                    IBinder.class, Application.class, Object.class);
            attachMethod.setAccessible(true);
            attachMethod.invoke(targetService, this, activityThread,
                    intent.getComponent().getClassName(), token, getApplication(), iActivityManager);
            //目标Service的onCreate方法
            targetService.onCreate();
            Log.d(TAG, "target Service created success!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "target Service created failed!");
            return START_STICKY;
        }
        targetService.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ProxyService onDestroy");
    }

}
