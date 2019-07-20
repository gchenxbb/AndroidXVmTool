package com.pa.chen.classloader;

import android.app.Application;


public class HApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.init(this);
    }
}
