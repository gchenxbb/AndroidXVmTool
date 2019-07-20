package com.pa.chen.classloader;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import dalvik.system.DexClassLoader;

/**
 * 自定义类加载器
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
        //加载器也需要被加载哟
        //他的类加载器是
        Log.d(TAG, "MyClassLoader:" + MyClassLoader.class.getClassLoader().toString());
    }

    //暂时注释，Dalvik虚拟机里走不通,不支持class
    // dexpath目前只支持“.dex”、“.jar”、“.apk”、“.zip”
    public Class findClass(String name) {
        byte[] data = loadClassData(name);
        return defineClass(data, 0, data.length);
    }

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