package com.pa.chen.classloader;

import android.util.Log;

public class Hello {

    public Hello() {
        super();
        Log.d("Hello", "hello class loader is " + Hello.class.getClassLoader());
    }

    public void helloV() {
        Log.d("Hello", "say Hello");
    }


}
