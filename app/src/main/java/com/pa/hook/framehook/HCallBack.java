package com.pa.hook.framehook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by yuan on 2018/12/7.
 */

public class HCallBack implements Handler.Callback {

    public static final String TAG="HCallBack";
    private Handler mHandler;
    public static final int LAUNCH_ACTIVITY         = 100;

    public HCallBack(Handler handler) {
        mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        //从ActivityThread中拿到LAUNCH_ACTIVITY的code
        if (msg.what == LAUNCH_ACTIVITY) {
            Log.d(TAG, "LAUNCH_ACTIVITY");
            Object object = msg.obj;
            //ActivityClientRecord类
            Class<?> objClass = object.getClass();
            try {
                Field field = objClass.getDeclaredField("intent");
                field.setAccessible(true);
                //获取的是ProxyActivity的Intent
                Intent proxyIntent = (Intent) field.get(object);
                Intent targetIntent=proxyIntent.getParcelableExtra(IActivityManagerProxy.INTENT_EXTRA);
                //替换Component,这样app收到的Intent，ComponentName就是目标Activity
                if(targetIntent!=null){
                    proxyIntent.setComponent(targetIntent.getComponent());
                }
//                后续就继续走系统正常的处理了
//                从msg的obj获取ActivityClientRecord，
//                触发handleLaunchActivity方法。如下代码
//                final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
//
//                r.packageInfo = getPackageInfoNoCheck(
//                        r.activityInfo.applicationInfo, r.compatInfo);
//                handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");

                //ams只知道ProxyActivity的存在。在app进程采用token和Activity进行标示，因此在其他生命周期方法时
                //ams只需要告诉token，然后就可以从map中找到匹配的ActivityClientRecord，被替换过内容的ActivityClientRecord
                //因此，其他生命周期方法也会走插件Activity的方法。
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        //每次还都会走mH的处理方法
        mHandler.handleMessage(msg);
        return true;
    }
}
