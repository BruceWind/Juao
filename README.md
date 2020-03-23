# HugestMemroyStorage

[中文](https://github.com/BruceWind/HugestFastestMemoryCache/blob/master/README_zh.md)

When you have a huge amount of memory that needs to be calculated on the Android device, may you think your can't alloc enough memory.
So I made this library. (Of course, not many people need this.)


If you need to do super-large image processing on device lower than Android 8.0, you may need this library.


I made an example of a Bitmap cache here. The access is really fast. In my test, I divided a panoramic image into many images, and then displayed them.


I don't recommend using this Repo to cache bitmaps on devices higher than 8.0, because [bitmap pixel data already is stored in native heap when device higher than 8.0](https://developer.android.google.cn/topic/performance/graphics/manage-memory).

When your memory is stored in **FastHugeStorage**, both put and pop will be very fast.


Also, I would enable close to 40% of the available hardware memory.


For most mobile phones, the running memory (ram) is already very large, but often the memory you requested cannot cover 40% of the hardware.


You can use this library to request such a large amount of memory.

Now,cache for Bitmap, Parcelable, and Serializable is supported by **FastHugeStorage**. Other types are cann't be supported. 
But bytes will be developed in the future,I'm Sorry.


## Build

1. Version of Android Studio must above V3.6.
2. I have built with 'NDK R20' and never built with other versions of ndk. I can't promise it successfully build on other version.You can try.
