package com.pa.hook.framehook;

import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//
public class IActivityManagerProxy implements InvocationHandler {
    public static final String TAG = "IActivityManagerProxy";
    public static final String INTENT_EXTRA = "TARGET_INTENT";

    private Object mActivityManager;

    public IActivityManagerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    //代理的对象方法触发这里，这里的方法是android.app.ActivityManager里的方法。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //拦截启动方法,將启动的插件Activity替换掉，换成在manifest占坑的代理Activity
        if ("startActivity".equals(method.getName())) {
            Log.d(TAG, method.getName() + "");

            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            Intent targetIntent = (Intent) args[index];
            Intent proxyIntent = new Intent();
            String packageName = "com.pa.hook";//包名和类名改成
            proxyIntent.setClassName(packageName, packageName + ".proxy.ProxyActivity");
            proxyIntent.putExtra(INTENT_EXTRA, targetIntent);
            args[index] = proxyIntent;//参数替换成代理Intent，将代理Activity向Ams注册验证
        } else if ("startService".equals(method.getName())) {
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            //拦截Intent,修改成代理Service
            intent = (Intent) args[index];
            Intent proxyIntent = new Intent();
            String packageName = "com.pa.hook";//packageName需要和appid包名一样
            proxyIntent.setClassName(packageName, packageName + ".proxy.ProxyService");
            //将目标Service保存起来
            proxyIntent.putExtra("target_service", intent.getComponent().getClassName());
            args[index] = proxyIntent;
        }
        return method.invoke(mActivityManager, args);
    }
}




