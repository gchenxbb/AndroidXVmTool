package com.pa.chen.classloader;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;


public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView = findViewById(R.id.classloader);
        mTextView.setText(new LoadClassLoader().parseStrClassLoader());

        loadAsseFile();

//        Hello hello = new Hello();
    }


    public byte[] loadAsseFile() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String[] files = null;
        try {

            files = getAssets().list("");
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                Log.d(TAG, fileName);
                if (fileName.contains(".apk")) {
                    String destPath = root + "/androidclassLoader/";
                    String destFileName = "temp.apk";
                    if (copyFileFromAssets(this, fileName, destPath, destFileName)) {
                        String dexfileName = root + "/androidclassLoader/" + "dex";
                        File dexOutputDir = new File(dexfileName);
                        if (!dexOutputDir.exists()) {
                            dexOutputDir.mkdirs();
                        }
                        String mNativeLibDirName = root + "/androidclassLoader/" + "lib";
                        File mNativeLibDir = new File(mNativeLibDirName);
                        if (!mNativeLibDir.exists()) {
                            mNativeLibDir.mkdirs();
                        }
//                        File dexOutputDir = getDir(destPath + "dex", Context.MODE_PRIVATE);
                        String dexOutputPath = dexOutputDir.getAbsolutePath();
//                        String mNativeLibDir = getDir(destPath + "lib", Context.MODE_PRIVATE).getAbsolutePath();
                        DexClassLoader classLoader = new DexClassLoader(destPath + destFileName, dexOutputPath, mNativeLibDirName, getClassLoader());
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName("com.android.myapp.Hello", true, classLoader);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (clazz == null) {
                            Log.d(TAG, "load hello,class,error!");
                        } else {
                            try {
                                Object object = clazz.newInstance();
                                Method method = clazz.getMethod("helloV");
                                method.invoke(object);
//                                hello.helloV();
                            } catch (Exception e) {
                                Log.d(TAG, "load hello,object,error!");
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //copy文件，从assets到sdk卡
    public boolean copyFileFromAssets(Context context, String apkName, String path, String fileName) {
        boolean flag = false;
        int BUFFER = 10240;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        AssetFileDescriptor fileDescriptor = null;
        byte b[] = null;
        try {
            InputStream inputStream = getAssets().open(apkName);
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(path + fileName);
            if (file.exists()) {
                file.delete();
            }

            in = new BufferedInputStream(inputStream, BUFFER);
            boolean isOK = file.createNewFile();
            if (isOK) {
                out = new BufferedOutputStream(new FileOutputStream(file), BUFFER);
                b = new byte[BUFFER];
                int read = 0;
                while ((read = in.read(b)) > 0) {
                    out.write(b, 0, read);
                }
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fileDescriptor != null) {
                    fileDescriptor.close();
                }
            } catch (IOException e) {
            }
        }
        return flag;
    }


}
