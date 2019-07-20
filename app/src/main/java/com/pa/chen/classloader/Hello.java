package com.pa.chen.classloader;

import android.util.Log;

public class Hello {

    public Hello() {
        super();
        Log.d("MainActivity", "hello class loader is " + Hello.class.getClassLoader());
    }

    public void helloV() {
        Log.d("MainActivity", "say Hello");
    }


}
