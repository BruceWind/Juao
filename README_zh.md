# HugestMemroyStorage
[English](https://github.com/BruceWind/HugestFastestMemoryCache/blob/master/README.md)
如果你有很大的内存需求,或许您需要这个库帮您做内存cache。

当您有巨量的内存需要在Android平台进行计算时，或许您也会遇到内存吃紧的问题，所以我做了这个库。（当然，有这种需求的人不多）

如果您需要在低于Android 8.0的机器上做超大的图片处理，可能会需要这个库。我这里做了个Bitmap的cache的例子，访问非常快在我的测试中，我把一张全景图片分割为多张，然后再展示。


高于8.0的android机器，不再推荐使用这种方式缓存bitmap，因为[Android 8.0（API 级别 26）及更高版本时，位图像素数据存储在原生堆中](https://developer.android.google.cn/topic/performance/graphics/manage-memory)

您的内存在这个类里面存储时，put和pop都会非常得快。另外，我会启用接近40%的可用硬件内存。对于大部分手机来说运行内存（ram）已经非常得大，而往往您申请的内存无法覆盖到硬件的40%。
您使用该库，即可申请这么大的内存。

目前支持对Bitmap,Parcelable,Serializable进行Cache.其他暂不支持。未来会开发缓存byte[]的功能。




## Build
1. 高于3.6版本的Android Studio.
2. 只在R20版本的ndk上面编译过，其他版本不保证编得过。
3. Proguard :
```
-keepclasseswithmembernames class * {
    native <methods>;
}
```