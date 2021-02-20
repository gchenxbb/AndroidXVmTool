package com.ld.mem.memory;

/**
 * shallow size = 49
 * <p>
 * 1+2+2+4+4+8+8+对象引用(4)+数组引用(4)+字符串引用(4)+Object基类两个变量(4+4) = 49
 * private transient Class<?> shadow$_klass_;
 * private transient int shadow$_monitor_;
 * <p>
 * <p>
 * retained size = 110
 * <p>
 * 在shallow size基础上，加上对象引用内容大小(37)，和数组内容大小(24)
 */
public class ObjHeapDemo {
    private boolean aBoolean = true;//1字节
    private char aChar = 'a';//2
    private short aShort = 15;//2
    private int anInt = 0;//4
    private float aFloat = 200.0f;//4
    private long aLong = 3000;//8
    private double aDouble = 1000000;//8
    private RefObject refObject = new RefObject();//4引用类型，对象内容是37字节
    private String string = "String";//4引用类型
    private int[] ints = {1, 2, 3, 4, 5, 6};//4,引用类型，数组内容是4*6=24字节
}
