package com.classloader.app;

import android.content.Context;
import android.util.Log;

/**
 *
 */
public class Hello {

    public Hello() {
        super();
        ClassLoader classLoader = Hello.class.getClassLoader();
        Log.d("MainActivity", "hello class loader is " + classLoader.getClass().getName());
    }

    public void helloV(Context context) {
        Log.d("MainActivity", "say Hello!");
    }


}
