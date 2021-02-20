package com.ld.mem;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class ActivityWacher {

    public static final String TAG = ActivityWacher.class.getSimpleName();

    public static void install(Context context) {
        Application application = (Application) context.getApplicationContext();
        ActivityWacher wacher = new ActivityWacher();
        application.registerActivityLifecycleCallbacks(wacher.lifecycleCallbacks);
    }

    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            if (activity != AppContextUtil.getTopActivity()) {
                //初始化全局topActivity
                AppContextUtil.setTopActivity(activity);
            }
            Log.d(TAG, "onActivityCreated:" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activity != AppContextUtil.getTopActivity()) {
                AppContextUtil.setTopActivity(activity);
            }
            Log.d(TAG, "onActivityStarted:" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (activity != AppContextUtil.getTopActivity()) {
                AppContextUtil.setTopActivity(activity);
            }
            Log.d(TAG, "onActivityResumed:" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused:" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, "onActivityStopped:" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity == AppContextUtil.getTopActivity()) {
                AppContextUtil.setTopActivity(null);//不置空会泄漏
            }
            Log.d(TAG, "onActivityDestroyed:" + activity.getClass().getSimpleName());
        }
    };

}
