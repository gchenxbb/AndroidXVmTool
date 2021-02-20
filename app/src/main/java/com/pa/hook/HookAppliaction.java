package com.pa.hook;

import android.app.Application;
import android.content.Context;

import com.pa.hook.framehook.HookHelper;

/**
 * Created by yuan on 2018/12/7.
 */

public class HookAppliaction extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            HookHelper.hookAms();
            HookHelper.hookHandler();
            //HookHelper.hookInstrumentation(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
