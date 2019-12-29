package com.classloader.app;

import android.content.Context;


public class Util {
    private static Context context;
    /*
     * 初始化
     */
    public static void init(Context context) {
        Util.context = context.getApplicationContext();
    }

    /*
     * 获取ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

}