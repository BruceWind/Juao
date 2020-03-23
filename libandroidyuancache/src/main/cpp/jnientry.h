//
// Created by bruce on 2020/3/16.
//
#include "jni.h"
#include <string.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)


#ifndef HUGESTFASTESTMEMORYCACHE_JNIENTRY_H
#define HUGESTFASTESTMEMORYCACHE_JNIENTRY_H

#ifdef __cplusplus
extern "C" {
#endif


JNIEXPORT jbyteArray JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_popData(JNIEnv *env, jobject obj, jint point);
JNIEXPORT jboolean JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_removeData(JNIEnv *env, jobject obj,
                                                               jint point);
JNIEXPORT jint JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_put(JNIEnv *env, jobject obj, jbyteArray data);
JNIEXPORT void JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_release(JNIEnv *env, jobject obj);

#ifdef __cplusplus
}
#endif


#endif
