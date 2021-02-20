package com.ld.mem;

import android.app.Activity;
import android.content.Context;

public class AppContextUtil {
    private static Context context;
    private static Activity topActivity;

    public static void init(Context context) {
        AppContextUtil.context = context.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static Activity getTopActivity() {
        return topActivity;
    }

    public static void setTopActivity(Activity topActivity) {
        AppContextUtil.topActivity = topActivity;
    }
}
