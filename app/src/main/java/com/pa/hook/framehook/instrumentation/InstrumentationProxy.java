package com.pa.hook.framehook.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.List;

//代理InstrumentationProxy
public class InstrumentationProxy extends Instrumentation {
    private PackageManager mPackageManager;
    private Instrumentation mInstrumentation;

    public InstrumentationProxy(PackageManager mPackageManager, Instrumentation mInstrumentation) {
        this.mPackageManager = mPackageManager;
        this.mInstrumentation = mInstrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        List<ResolveInfo> infos = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        //未找到启动的Intent组件，未在manifest注册
        if (infos == null || infos.size() == 0) {
            intent.putExtra("target_intent", intent.getComponent().getClassName());
            intent.setClassName(who, "com.pa.hook.proxy.ProxyActivity");
        }
        try {
            Method method = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class
                    , IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            return (ActivityResult) method.invoke(mInstrumentation, who, contextThread, token, target,
                    intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String intentName = intent.getStringExtra("target_intent");
        if (!TextUtils.isEmpty(intentName)) {//更换className貌似启动的还是proxyactivity
          return super.newActivity(cl,intentName,intent);
        }
        return super.newActivity(cl, className, intent);
    }
}
