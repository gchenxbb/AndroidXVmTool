package com.ld.mem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.classloader.app.R;

public class MemoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int m = 0; m < 3; m++) {
                    for (int i = 0; i < 100000000; i++) {
                        Object object = new Object();
                    }
                }
            }
        });

        MemUtil.memInfo();
    }
}