//
// Created by bruce on 2020/3/16.
//
#include "jnientry.h"
#include <vector>
#include <map>

using namespace std;


static std::map<int, char *> static_map;
static int ticket_point = 0;


char *to_chars(JNIEnv *env, jbyteArray bytearray) {
    char *chars = NULL;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(bytearray, 0);
    int chars_len = env->GetArrayLength(bytearray);
    chars = new char[chars_len + 1];
    memset(chars, 0, chars_len + 1);
    memcpy(chars, bytes, chars_len);
    chars[chars_len] = 0;

    env->ReleaseByteArrayElements(bytearray, bytes, 0);

    return chars;
}

jbyteArray to_jbyteArray(JNIEnv *env, char *data, int length) {
    jbyte *by = (jbyte *) data;
    jbyteArray jarray = env->NewByteArray(length);
    env->SetByteArrayRegion(jarray, 0, length, by);
    return jarray;
}


/**
 * is popdata
 * @param env
 * @param jobj
 * @param p
 * @return
 */
JNIEXPORT jbyteArray JNICALL
Java_com_androidyuan_libcache_nativecache_NativeEntry_popData(JNIEnv *env, jobject jobj, jint p) {
    map<int, char *>::iterator iter;
    iter = static_map.find(p);
    if (iter != static_map.end()) {
        jbyteArray result = to_jbyteArray(env, iter->second, strlen(iter->second));
        static_map.erase(iter);
        return result;
    } else {
        return NULL;
    }
}

JNIEXPORT jboolean JNICALL
Java_com_androidyuan_libcache_nativecache_NativeEntry_removeData(JNIEnv *env, jobject obj, jint p) {
//    static_data_arr.erase(*point);
    map<int, char *>::iterator iter;

    iter = static_map.find(p);
    if (iter != static_map.end()) {
        static_map.erase(iter);
        return JNI_TRUE;
    } else {
        return JNI_FALSE;
    }
}


JNIEXPORT jint JNICALL
Java_com_androidyuan_libcache_nativecache_NativeEntry_put(JNIEnv *env, jobject obj,
                                                          jbyteArray data) {

    char *pCData = to_chars(env, data);
    ++ticket_point;
    static_map.insert(pair<int, char *>(ticket_point, pCData));
    return ticket_point;
}


JNIEXPORT void JNICALL
Java_com_androidyuan_libcache_nativecache_NativeEntry_release(JNIEnv *env, jobject obj) {
    static_map.clear();
}