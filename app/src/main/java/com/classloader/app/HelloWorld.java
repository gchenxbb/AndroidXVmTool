package com.classloader.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * 外部apk中的类
 * 全限定名是：com.ft.helloword.HelloWorld
 * 这里引入主要是查看内容
 */
public class HelloWorld {

    public HelloWorld() {
        super();
        ClassLoader classLoader = HelloWorld.class.getClassLoader();
        Log.d("MainActivity", "hello class loader is " + classLoader.getClass().getName());
        Log.d("MainActivity", "hello class loader is " + HelloWorld.class.getClassLoader());
    }

    public void helloV(Context context) {
        Toast.makeText(context, "外部apk say Hello!", Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "外部apk say Hello!");
    }


}
