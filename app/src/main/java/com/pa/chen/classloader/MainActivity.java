package com.pa.chen.classloader;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classLoaderLog();
        TextView mTextView = findViewById(R.id.classloader);
        mTextView.setText(Html.fromHtml(stringBuilder.toString()));

}

    public void classLoaderLog() {
        try {
            // 获取classes的dexElements
            Class<?> cl = Class.forName("dalvik.system.BaseDexClassLoader");

            //Context内部的类加载器：dalvik.system.PathClassLoader加载器
            ClassLoader parent = Util.getContext().getClassLoader();
            Log.d(TAG, parent.toString());
            stringBuilder.append("<font color=\"#FE6026\">Context内部的类加载器：</font>\n");
            stringBuilder.append(parent.toString() + "\n");

            //ClassLoader内部SystemClassLoader，dalvik.system.PathClassLoader加载器
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            Log.d(TAG, systemLoader.toString());
            stringBuilder.append("<font color=\"#FE6026\">ClassLoader内部SystemClassLoader类加载器：</font>\n");
            stringBuilder.append(systemLoader.toString() + "\n");

            if (systemLoader.getParent() != null) {
                ClassLoader bootLoader = systemLoader.getParent();
                if (bootLoader != null) {
                    Log.d(TAG, bootLoader.toString());
                    stringBuilder.append("<font color=\"#FE6026\">SystemClassLoader的父类加载器：</font>\n");
                    stringBuilder.append(bootLoader.toString() + "\n");
                }
            }

            //dalvik.system.PathClassLoader当前类加载器
            String currentLoader = Thread.currentThread().getContextClassLoader().toString();
            Log.d(TAG, currentLoader);
            stringBuilder.append("<font color=\"#FE6026\">当前线程类加载器：</font>\n");
            stringBuilder.append(currentLoader + "\n");

            stringBuilder.append("<font color=\"#FE6026\">Class的加载器：</font>\n");
            //java.lang.BootClassLoader系统类System的加载器
            String sysBootLoader = System.class.getClassLoader().toString();
            Log.d(TAG, sysBootLoader);
            stringBuilder.append("<font color=\"#FE6026\">System.class的加载器：</font>\n");
            stringBuilder.append(sysBootLoader + "\n");

            //java.lang.BootClassLoader系统class的加载器
            String actBootLoader = Activity.class.getClassLoader().toString();
            Log.d(TAG, actBootLoader);
            stringBuilder.append("<font color=\"#FE6026\">Activity.class的加载器：</font>\n");
            stringBuilder.append(actBootLoader + "\n");

            //dalvik.system.PathClassLoader，用户类的加载器
            String helloLoader = Hello.class.getClassLoader().toString();
            Log.d(TAG, helloLoader);
            stringBuilder.append("<font color=\"#FE6026\">Hello.class的加载器：</font>\n");
            stringBuilder.append(helloLoader + "\n");

            //dalvik.system.PathClassLoader，用户类的加载器
            String MainActivityLoader = MainActivity.class.getClassLoader().toString();
            Log.d(TAG, MainActivityLoader);
            stringBuilder.append("<font color=\"#FE6026\">MainActivity.class的加载器：</font>\n");
            stringBuilder.append(MainActivityLoader + "\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        //自定义加载类加载器
//        MyClassLoader cl1 = new MyClassLoader();
//        try {
//            //加载,用以下Hello转换成jar
//            Class c1 = cl1.loadClass("Hello");
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


    }

}
