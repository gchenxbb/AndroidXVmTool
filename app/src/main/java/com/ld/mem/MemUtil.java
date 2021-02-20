package com.ld.mem;

import android.util.Log;

public class MemUtil {

    public static void memInfo() {
        int M = 1024 * 1024;

        Runtime runtime = Runtime.getRuntime();
        long maxMem = runtime.maxMemory();
        long totalMem = runtime.totalMemory();
        long freeMem = runtime.freeMemory();

        Log.d("memUtil", "最大可用内存:" + maxMem + "(" + maxMem / M + "M)"
                + "，当前可用内存:" + totalMem + "(" + totalMem / M + "M)"
                + "，当前空闲内存:" + freeMem + "(" + freeMem / M + "M)");

    }
}
