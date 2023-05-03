# Juao ![Build APK](https://github.com/BruceWind/Juao/workflows/Build%20APK/badge.svg?branch=master)![runUnitTest](https://github.com/BruceWind/Juao/workflows/runUnitTest/badge.svg)

[中文](https://github.com/BruceWind/Juao/blob/master/README_zh.md)

> [Juao](https://www.wikiwand.com/zh-cn/%E9%B3%8C) (巨鳌), a hugest animal in traditonal Chinese legend, looks like a tortoise could float up moutain or land from the ocean.

![](https://github.com/BruceWind/Juao/raw/master/image/juao.png)

In most Android devices, enven though the physical memory (RAM) is very large, but often the memory that app can use is not much more than [JVM heap size](https://developer.android.com/topic/performance/memory#CheckHowMuchMemory).
When you have a huge amount of memory that needs to calculate on Android, may you think your can't alloc enough memory.
So I made this library. (It is fact that just few people get in the situation.)
If you need to deal with super-large image on devices which is lower than Android 8.0, you must need this library.

While your memory is caching in **Juao**, both put and pop will be very fast.
I made an example of caching Bitmap in there. The bitmap put and get from cache is really fast. In the example, I divided a panoramic image into many images, and then merged those to display.


I don't recommend using this repo to cache bitmaps on devices higher than 8.0, because [bitmap pixel data was already stored in native heap when device higher than 8.0](https://developer.android.google.cn/topic/performance/graphics/manage-memory).


In addition, I would enable about 40% of the physical RAM.


Currently,caching for **Bitmap, Parcelable, and Serializable** are supported by **Juao**. It capable of supporting other types. 

I may develop caching byte[] in the future.

## How it work?
Use [DirectByteBuffer](https://chromium.googlesource.com/android_tools/+/2403/sdk/sources/android-22/java/nio/DirectByteBuffer.java) 
 to cache bytes on memory which is physical memory. If cache bytes over limitation that you set, bytes will be cached on disk.
