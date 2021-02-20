package com.pa.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.classloader.app.R;
import com.pa.hook.target.TargetActivity;
import com.pa.hook.target.TargetService;

public class MainHookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhook);

        // 启动插件activity
        findViewById(R.id.btn_start_activityplugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHookActivity.this,TargetActivity.class));
            }
        });

        // 启动插件service
        findViewById(R.id.btn_start_serviceplugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainHookActivity.this,TargetService.class));
            }
        });
    }


}
