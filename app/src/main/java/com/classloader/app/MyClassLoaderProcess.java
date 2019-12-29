package com.classloader.app;

import android.util.Log;

/**
 * 自定义类加载器
 */
public class MyClassLoaderProcess {

    public static final String TAG = "MyClassLoaderProcess";

    public MyClassLoaderProcess() {
    }

    public void myLoader() {
        //自定义加载类加载器
        MyClassLoader myClassLoader = new MyClassLoader();
        try {
            //加载,用以下Hello转换成jar
            Class c1 = myClassLoader.loadClass("Hello");
            if (c1 == null) {
                Log.d(TAG, "error: loaded class is null");
                return;
            }
            String object = c1.newInstance().getClass().toString();
            Log.d("Hello", object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "main-ClassNotFoundException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        } catch (InstantiationException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}