package com.ld.mem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Virm {
    public static boolean getIsArtInUse() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }
    public static String getCurrentRuntimeName() {
        String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
        String LIB_DALVIK = "libdvm.so";
        String LIB_ART = "libart.so";
        String LIB_ART_D = "libartd.so";
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get",
                        String.class, String.class);
                if (get == null) {
                    return "未获取到";
                }
                try {
                    final String value = (String) get.invoke(
                            systemProperties, SELECT_RUNTIME_PROPERTY,
                            /* Assuming default is */"Dalvik");
                    if (LIB_DALVIK.equals(value)) {
                        return "Dalvik";
                    } else if (LIB_ART.equals(value)) {
                        return "ART";
                    } else if (LIB_ART_D.equals(value)) {
                        return "ART debug build";
                    }
                    return value;
                } catch (IllegalAccessException e) {
                    return "IllegalAccessException";
                } catch (IllegalArgumentException e) {
                    return "IllegalArgumentException";
                } catch (InvocationTargetException e) {
                    return "InvocationTargetException";
                }
            } catch (NoSuchMethodException e) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (ClassNotFoundException e) {
            return "SystemProperties class is not found";
        }
    }
}
