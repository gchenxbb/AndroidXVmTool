package com.classloader.app;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import dalvik.system.DexClassLoader;

/**
 * 自定义类加载器
 * 目标，可以加载apk外部的类，包括apk，dex等，类似DexClassLoader
 * Dalvik虚拟机不支持class文件，直接加载class走不通。
 */
public class MyClassLoader extends ClassLoader {

    public DexClassLoader dexClassLoader;
    public static final String TAG = "MyClassLoader";
    public static final String driver = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator;
    public static final String fileTyep = ".class";

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyClassLoader() {
        //自定义的加载器也需要被加载哟，它的类加载器是PathClassLoader
        ClassLoader classLoader = MyClassLoader.class.getClassLoader();
        Log.d(TAG, "MyClassLoader:" + classLoader.getClass().getName());
    }

    //dexpath目前只支持“.dex”、“.jar”、“.apk”、“.zip”
    @Override
    public Class findClass(String name) {
        byte[] data = loadClassData(name);
        if (data == null) {
            return null;
        }
        return defineClass(data, 0, data.length);
    }

    //加载外部jar文件
    public byte[] loadClassData(String name) {
        FileInputStream fis = null;
        byte[] data = null;
        try {
            String loaction = driver + name + fileTyep;
            File file = new File(loaction);
            System.out.println(file.getAbsolutePath());
            fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = fis.read()) != -1) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("loadClassData-IOException");
        }
        return data;
    }
}