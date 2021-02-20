package com.pa.hook.target;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//插件Service,未注册
public class TargetService extends Service {
    private final String TAG = "TargetService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"TargetService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"TargetService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"TargetService onDestroy");
    }
}
