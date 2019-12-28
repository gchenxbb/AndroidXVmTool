Android的ClassLoader
加载的是dex文件。

技术文档

[双亲委派模型](https://www.jianshu.com/p/74685bdddf22)

#### 系统加载器
BootClassLoader，Java实现，同一个包才可以访问，App无法访问
DexClassLoader，继承BaseDexClassLoader，加载dex文件，apk，jar文件。最终都是要加载dex文件。
参数optimizedDirectory是解压dex文件的内部存储路径，私有路径/data/data/<Package Name>/..,
PathClassLoader，继承BaseDexClassLoader，默认参数optimizedDirectory是/data/dalvik_cache，加载已安装的apk的dex文件，存储在该路径。

MainActivity.class,Hello.class的加载器是PathClassLoader。
路径是
dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/base.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_dependencies_apk.apk",
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_0_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_1_apk.apk",
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_2_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_3_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_4_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_5_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_6_apk.apk",
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_7_apk.apk", 
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_8_apk.apk",
zip file "/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/split_lib_slice_9_apk.apk"],
nativeLibraryDirectories=[/data/app/com.pa.chen.classloader-YA45x9vXJeZnGEbKV2Cm5w==/lib/arm64, /system/lib64, /vendor/lib64]]]

Activity.class，Context.class，System.class的加载器是BootClassLoader。

android源码26api，ClassLoader内部SystemClassLoader存储的加载器是PathClassLoader。父类加载器是BootClassLoader。

ContextImpl内部加载器来自LoadedApk的getClassLoader。LoadApk是空则来自ClassLoader内部SystemClassLoader。
