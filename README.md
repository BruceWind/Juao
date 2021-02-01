HugestMemroyStorage
-----------
[中文](https://github.com/BruceWind/HugestFastestMemoryCache/blob/master/README_zh.md)
![Build APK](https://github.com/BruceWind/HugestFastestMemoryCache/workflows/Build%20APK/badge.svg?branch=master)![runUnitTest](https://github.com/BruceWind/HugestFastestMemoryCache/workflows/runUnitTest/badge.svg)


When you have a huge amount of memory that needs to be calculated on the Android device, may you think your can't alloc enough memory.
So I made this library. (Of course, not many people need this.)


If you need to do super-large image processing on device which is lower than Android 8.0, you may need this library.


I made an example of a Bitmap cache here. The access is really fast. In my example, I divided a panoramic image into many images, and then displayed them.


I don't recommend using this Repo to cache bitmaps on devices higher than 8.0, because [bitmap pixel data was already stored in native heap when device higher than 8.0](https://developer.android.google.cn/topic/performance/graphics/manage-memory).

As your memory caching in **HugestFastestMemoryCache**, both put and pop will be very fast.


PS, I would enable about 40% of the physical RAM.


For most mobile phones, the running memory (ram) is 
very large, but often the memory that you requested cant cover 40% of the physical RAM.


You can use this library to cache such a large amount of memory.

Currently,caching for Bitmap, Parcelable, and Serializable were supported by **HugestFastestMemoryCache**. It couldn't support other types. 

May I will develop caching byte[] in the future.

## How it work?
Use [DirectByteBuffer](https://chromium.googlesource.com/android_tools/+/2403/sdk/sources/android-22/java/nio/DirectByteBuffer.java) 
 to cache bytes on memory which is physical memory. If cache bytes over limitation that you set, bytes will be cached on disk.
