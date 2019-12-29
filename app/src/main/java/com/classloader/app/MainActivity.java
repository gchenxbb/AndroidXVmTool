package com.classloader.app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.DexClassLoader;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = "MainActivity";
    //路径
    private String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String destPath = rootDir + "/androidclassLoader/";//assets复制目录
    private String dexOutputPath = destPath + "dex";//解析dex目录
    private String mNativeLibOutputPath = destPath + "lib";//解析lib目录
    private String destFileName = "classloader_temp.apk";//assets复制目标文件
    private String destFileOutputPath = destPath + destFileName;//目标文件
    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Button mBtn;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.classloader);
        mBtn = findViewById(R.id.btn_classloader);
        mTextView.setText(new LoadClassLoaderParse().parseStrClassLoader());

        if (EasyPermissions.hasPermissions(this, perms)) {
        } else {
            EasyPermissions.requestPermissions(this, "需要请求读写权限！",
                    0x0010, perms);
        }

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(MainActivity.this);
                alertdialogbuilder.setMessage("提示！即将DexClassLoader加载外部apk，先从assets复制到sd卡");
                alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadAssetFile();
                    }
                });
                alertdialogbuilder.setNeutralButton("取消", null);
                alertdialogbuilder.create().show();

            }
        });

        //关于自定义类加载起暂时注释
        new MyClassLoaderProcess().myLoader();

    }

    //加载assets文件
    public byte[] loadAssetFile() {
        Log.d(TAG, rootDir);
        String[] files = null;
        try {
            files = getAssets().list("");
            if (files == null) {
                return null;
            }
            Log.d(TAG, "assets file num:" + files.length);
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                Log.d(TAG, fileName);
                if (fileName.contains(".apk")) {
                    if (copyFileFromAssets(this, fileName, destPath, destFileName)) {
                        File dexOutputDir = new File(dexOutputPath);
                        if (!dexOutputDir.exists()) {
                            dexOutputDir.mkdirs();
                        }
                        File mNativeLibDir = new File(mNativeLibOutputPath);
                        if (!mNativeLibDir.exists()) {
                            mNativeLibDir.mkdirs();
                        }
                        loadAndInvokeClass();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * copy文件，从assets到sdk卡
     *
     * @param context
     * @param apkName  源文件名
     * @param path     目标路径
     * @param fileName 目标名
     * @return
     */
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

    //使用DexClassLoader加载Class文件
    private void loadAndInvokeClass() {
        ClassLoader classLoader = getClassLoader();
        Log.d(TAG, "父加载器parent：" + classLoader.getClass().getName());
        DexClassLoader dexClassLoader = new DexClassLoader(destFileOutputPath, dexOutputPath, mNativeLibOutputPath, classLoader);
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.ft.helloword.HelloWorld", true, dexClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "loadAndInvokeClass hello,class,error!" + e.getMessage());
        }
        if (clazz == null) {
            Log.d(TAG, "loadAndInvokeClass hello,class,error!");
        } else {
            try {
                Object object = clazz.newInstance();
                Method method = clazz.getMethod("helloV", Context.class);
                method.invoke(object, MainActivity.this);
            } catch (Exception e) {
                Log.d(TAG, "loadAndInvokeClass hello,object,error!" + e.getMessage());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        Log.d(TAG, "requestCode:" + requestCode);
        Log.d(TAG, "list:" + list.size());
        Toast.makeText(MainActivity.this, "因为无权限，且不再提醒，拒绝进入，请自己设置!", Toast.LENGTH_LONG).show();
        finish();
    }
}
