package com.classloader.app;

import android.app.Application;
import android.util.Log;

import com.ld.mem.ActivityWacher;
import com.ld.mem.MemUtil;
import com.ld.mem.Virm;
//import com.squareup.leakcanary.LeakCanary;

public class HApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.init(this);

        //监控内存
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
//        }

        ActivityWacher.install(this);

        MemUtil.memInfo();

        String value = Virm.getCurrentRuntimeName();
        Log.d("memUtil", "虚拟机:" + value);

        Log.d("memUtil", "虚拟机art:" + Virm.getIsArtInUse());
    }
}
