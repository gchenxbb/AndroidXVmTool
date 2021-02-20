package com.ld.mem.memory;

import android.content.Context;

public class CommonUtil {

    private static CommonUtil instance;
    private Context context;

    private CommonUtil(Context context) {
        this.context = context;
    }

    public static CommonUtil getInstance(Context context) {
        if (null == instance) {
            instance = new CommonUtil(context);
        }
        return instance;
    }
}
