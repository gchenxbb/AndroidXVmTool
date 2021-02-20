package com.ld.mem.memory;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.classloader.app.R;


public class DemoActivity extends Activity {

    ObjHeapDemo o = new ObjHeapDemo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

//        CommonUtil inst = CommonUtil.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
