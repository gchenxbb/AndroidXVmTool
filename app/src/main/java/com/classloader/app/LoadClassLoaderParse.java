package com.classloader.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * 查看类加载器
 * <p>
 * dalvik.system.PathClassLoader
 * dalvik.system.DexClassLoader
 * dalvik.system.BaseDexClassLoader
 */
public class LoadClassLoaderParse {
    public static final String TAG = "LoadClassLoaderParse";
    StringBuilder sb = new StringBuilder();

    public String parseStrClassLoader() {

        try {
            // 获取classes的dexElements
            Class<?> cl = Class.forName("dalvik.system.BaseDexClassLoader");

            contextClassLoader();//PathClassLoader

            systemClassLoader();//PathClassLoader

            activityClassLoader();//java.lang.BootClassLoader

            myActivityClassLoader();//PathClassLoader

            stringClassLoader();//java.lang.BootClassLoader

            helloClassLoader();//PathClassLoader

            selfClassLoader();//PathClassLoader

            threadInClassLoader();//PathClassLoader

        } catch (ClassNotFoundException e) {

            Log.d(TAG, e.getMessage());
        }

        return sb.toString();
    }

    //Context内部的类加载器：dalvik.system.PathClassLoader加载器
    private void contextClassLoader() {
        ClassLoader classLoader = Util.getContext().getClassLoader();
        String contextLoader = classLoader.getClass().getName();
        Log.d(TAG, contextLoader);
        sb.append("Context内部的类加载器：");
        sb.append("\n");
        sb.append(contextLoader);
        sb.append("\n\n");

        ClassLoader contextClassLoader = Context.class.getClassLoader();
        String cxtClassLoader = contextClassLoader.getClass().getName();
        Log.d(TAG, cxtClassLoader);
        sb.append("类Context的类加载器：");
        sb.append("\n");
        sb.append(cxtClassLoader);
        sb.append("\n\n");
    }

    //ClassLoader内部SystemClassLoader类加载器：dalvik.system.PathClassLoader加载器
    private void systemClassLoader() {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        String sysClassLoader = systemLoader.getClass().getName();
        Log.d(TAG, sysClassLoader);
        sb.append("ClassLoader内部保存的静态类SystemClassLoader中loader：");
        sb.append("\n");
        sb.append(sysClassLoader);
        sb.append("\n\n");


        if (systemLoader.getParent() != null) {
            ClassLoader bootLoader = systemLoader.getParent();
            String bootClassLoader = bootLoader.getClass().getName();
            Log.d(TAG, bootClassLoader);
            sb.append("SystemClassLoader的父类加载器：");
            sb.append("\n");
            sb.append(bootClassLoader);
            sb.append("\n\n");

        }
    }

    //Activity类的类加载器，java.lang.BootClassLoader系统class的加载器
    private void activityClassLoader() {
        String actBootLoader = Activity.class.getClassLoader().toString();
        Log.d(TAG, actBootLoader);
        sb.append("系统类Activity.class的加载器：");
        sb.append("\n");
        sb.append(actBootLoader);
        sb.append("\n\n");
    }

    //MainActivity类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
    private void myActivityClassLoader() {
        ClassLoader classLoader = MainActivity.class.getClassLoader();
        String myActivityLoader = classLoader.getClass().getName();
        Log.d(TAG, myActivityLoader);
        sb.append("自定义类MainActivity.class的加载器：");
        sb.append("\n");
        sb.append(myActivityLoader);
        sb.append("\n\n");

        ClassLoader classLoaderSuper = MainActivity.class.getClassLoader().getParent();
        String myActivitySuperLoader = classLoaderSuper.getClass().getName();
        sb.append("自定义MainActivity.class的加载器的父加载器：");
        sb.append("\n");
        sb.append(myActivitySuperLoader);
        sb.append("\n\n");
    }

    //String类的类加载器，java.lang.BootClassLoader系统class的加载器
    private void stringClassLoader() {
        ClassLoader classLoader = String.class.getClassLoader();
        String sysBootLoader = classLoader.getClass().getName();
        Log.d(TAG, sysBootLoader);
        sb.append("系统类String.class的类加载器：");
        sb.append("\n");
        sb.append(sysBootLoader);
        sb.append("\n\n");
    }

    //自定义Hello类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
    private void helloClassLoader() {
        ClassLoader classLoader = Hello.class.getClassLoader();
        String helloLoader = classLoader.getClass().getName();
        Log.d(TAG, helloLoader);
        sb.append("自定义类Hello.class的加载器：");
        sb.append("\n");
        sb.append(helloLoader);
        sb.append("\n\n");
    }

    //当前类的类加载器，dalvik.system.PathClassLoader，用户类的加载器
    private void selfClassLoader() {
        ClassLoader classLoader = LoadClassLoaderParse.class.getClassLoader();
        String selfLoader = classLoader.getClass().getName();
        Log.d(TAG, selfLoader);
        sb.append("当前类LoadClassLoaderParse.class的加载器：");
        sb.append("\n");
        sb.append(selfLoader);
        sb.append("\n\n");
    }

    //当前线程内部的类加载器，dalvik.system.PathClassLoader当前类加载器
    private void threadInClassLoader() {
        Thread thread = Thread.currentThread();
        ClassLoader classLoader = thread.getContextClassLoader();
        String currentLoader = classLoader.getClass().getName();
        Log.d(TAG, currentLoader);
        sb.append("当前线程类Thread内部的加载器：");
        sb.append("\n");
        sb.append(currentLoader);
        sb.append("\n\n");
    }

}
