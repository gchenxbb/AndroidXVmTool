package com.pa.hook.target;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.classloader.app.R;

//目标Activity，未在AndroidManifest中注册，通过hook技术启动。
public class TargetActivity extends Activity {
    private final String TAG="TargetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        Log.d(TAG,"TargetActivity onCreate");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"TargetActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"TargetActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"TargetActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"TargetActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"TargetActivity onDestroy");
    }
}
