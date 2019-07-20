package com.pa.chen.classloader;

import android.app.Activity;
import android.util.Log;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * 查看某些类的类加载器
 */
public class LoadClassLoader {
    public static final String TAG = "LoadClassLoader";
    StringBuilder sb = new StringBuilder();

    PathClassLoader pathClassLoader;
    DexClassLoader dexClassLoader;

    public String parseStrClassLoader() {

        try {

            // 获取classes的dexElements
            Class<?> cl = Class.forName("dalvik.system.BaseDexClassLoader");

            contextClassLoader();//PathClassLoader

            systemClassLoader();//PathClassLoader

            threadInClassLoader();//PathClassLoader

            activityClassLoader();//java.lang.BootClassLoader
            myActivityClassLoader();//PathClassLoader

            stringClassLoader();//java.lang.BootClassLoader

            helloClassLoader();//PathClassLoader

            selfClassLoader();//PathClassLoader

        } catch (ClassNotFoundException e) {


        }

        //自定义加载类加载器
//        MyClassLoader myClassLoader = new MyClassLoader();
//        try {
//            //加载,用以下Hello转换成jar
//            Class c1 = myClassLoader.loadClass("Hello");
//            String object = c1.newInstance().getClass().toString();
//            Log.d("Hello", object);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.d(TAG, "main-ClassNotFoundException");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }

        return sb.toString();
    }

    /**
     * Context内部的类加载器：dalvik.system.PathClassLoader加载器
     */
    private void contextClassLoader() {
        ClassLoader parent = Util.getContext().getClassLoader();
        Log.d(TAG, parent.toString());
        sb.append("Context内部的类加载器：");
        sb.append(parent.toString() + "\n\n");
    }

    /**
     * ClassLoader内部SystemClassLoader类加载器：dalvik.system.PathClassLoader加载器
     */
    private void systemClassLoader() {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        Log.d(TAG, systemLoader.getClass().getSimpleName());
        sb.append("ClassLoader内部SystemClassLoader类加载器：");
        sb.append(systemLoader.toString() + "\n\n");

        if (systemLoader.getParent() != null) {
            ClassLoader bootLoader = systemLoader.getParent();
            if (bootLoader != null) {
                Log.d(TAG, bootLoader.toString());
                sb.append("SystemClassLoader的父类加载器：");
                sb.append(bootLoader.toString() + "\n\n");
            }
        }
    }

    /**
     * Activity类的类加载器，java.lang.BootClassLoader系统class的加载器
     */
    private void activityClassLoader() {
        String actBootLoader = Activity.class.getClassLoader().toString();
        Log.d(TAG, actBootLoader);
        sb.append("Activity.class的加载器：");
        sb.append(actBootLoader + "\n\n");
    }

    /**
     * MainActivity类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
     */
    private void myActivityClassLoader() {
        String myActivityLoader = MainActivity.class.getClassLoader().toString();
        Log.d(TAG, myActivityLoader);
        sb.append("MainActivity.class的加载器：");
        sb.append(myActivityLoader + "\n\n");
        sb.append("MainActivity.class的加载器的父加载器：");
        sb.append(MainActivity.class.getClassLoader().getParent().toString() + "\n\n");
    }

    /**
     * String类的类加载器，java.lang.BootClassLoader系统class的加载器
     */
    private void stringClassLoader() {
        String sysBootLoader = String.class.getClassLoader().toString();
        Log.d(TAG, sysBootLoader);
        sb.append("String.class的加载器：");
        sb.append(sysBootLoader + "\n\n");
    }

    /**
     * 自定义Hello类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
     */
    private void helloClassLoader() {
        String helloLoader = Hello.class.getClassLoader().toString();
        Log.d(TAG, helloLoader);
        sb.append("Hello.class的加载器：");
        sb.append(helloLoader + "\n\n");
    }

    /**
     * 当前类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
     */
    private void selfClassLoader() {
        String selfLoader = LoadClassLoader.class.getClassLoader().toString();
        Log.d(TAG, selfLoader);
        sb.append("LoadClassLoader.class的加载器：");
        sb.append(selfLoader + "\n\n");
    }

    /**
     * 当前线程内部的类加载器，dalvik.system.PathClassLoader当前类加载器
     */
    private void threadInClassLoader() {
        String currentLoader = Thread.currentThread().getContextClassLoader().toString();
        Log.d(TAG, currentLoader);
        sb.append("当前线程类加载器：");
        sb.append(currentLoader + "\n\n");
    }

}
