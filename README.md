# Juao ![Build APK](https://github.com/BruceWind/Juao/workflows/Build%20APK/badge.svg?branch=master)![runUnitTest](https://github.com/BruceWind/Juao/workflows/runUnitTest/badge.svg)

[中文](https://github.com/BruceWind/HugestFastestMemoryCache/blob/master/README_zh.md)

> In traditonal Chinese legend, [Juao](https://www.wikiwand.com/zh-cn/%E9%B3%8C) (巨鳌) is a hugest animal, looks like a tortoise could float up moutain or land from the ocean.

![](https://github.com/BruceWind/Juao/raw/master/image/juao.png)

For most mobile phones, the running memory (ram) is very large, but often the memory that app can use is not much than [App heap size](https://developer.android.com/topic/performance/memory#CheckHowMuchMemory) .

When you have a huge amount of memory that needs to be calculated on the Android device, may you think your can't alloc enough memory.

So I made this library. (It is correct that just few people will been the situation.)


If you need to do super-large image processing on devices which is lower than Android 8.0, you may need this library.


I made an example of a Bitmap cache here. The access is really fast. In the example, I divided a panoramic image into many images, and then displayed those.


I don't recommend using this Repo to cache bitmaps on devices higher than 8.0, because [bitmap pixel data was already stored in native heap when device higher than 8.0](https://developer.android.google.cn/topic/performance/graphics/manage-memory).

As your memory caching in **Juao**, both put and pop will be very fast.


In addition, I would enable about 40% of the physical RAM.




Currently,caching for Bitmap, Parcelable, and Serializable were supported by **HugestFastestMemoryCache**. It couldn't support other types. 

May I will develop caching byte[] in the future.

## How it work?
Use [DirectByteBuffer](https://chromium.googlesource.com/android_tools/+/2403/sdk/sources/android-22/java/nio/DirectByteBuffer.java) 
 to cache bytes on memory which is physical memory. If cache bytes over limitation that you set, bytes will be cached on disk.
