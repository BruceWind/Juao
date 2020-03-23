//
// Created by bruce on 2020/3/16.
//
#include "jnientry.h"
#include <vector>
#include <map>

using namespace std;


static std::map<int, char *> static_data_map;
static std::map<int, int> static_len_map;//I have to save len of char* to a map due to char* will loss data when char* contain 0;
static int ticket_point = 0;

/**
 * whatever data contains end character as 0.
 * @param data
 */
void recycleData(char *data) {
    delete[] data;
}

void recycleData(map<int, char *> data) {
    data.clear();
    map<int, char *> empty;
    empty.swap(data);
}

void recycleData(map<int, int> data) {
    data.clear();
    map<int, int> empty;
    empty.swap(data);
}


char *to_chars(JNIEnv *env, jbyteArray bytearray) {
    char *chars = NULL;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(bytearray, 0);
    int chars_len = env->GetArrayLength(bytearray);
    chars = new char[chars_len + 1];
    memset(chars, 0, chars_len + 1);
    memcpy(chars, bytes, chars_len);
    chars[chars_len] = 0;
    static_len_map.insert(pair<int, int>(ticket_point, chars_len));

    env->ReleaseByteArrayElements(bytearray, bytes, 0);

    return chars;
}

/**
 * char* to jbyteArray
 * @param env
 * @param data
 * @param length
 * @return
 */
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
Java_com_androidyuan_libcache_fastcache_NativeEntry_popData(JNIEnv *env, jobject jobj, jint p) {
    auto iter = static_data_map.find(p);
    if (iter != static_data_map.end()) {
        jbyteArray result = to_jbyteArray(env, iter->second, static_len_map.find(p)->second);
        //recycler.
        static_data_map.erase(iter);
        recycleData(iter->second);

        auto iter_en = static_len_map.find(p);
        static_len_map.erase(iter_en);
        return result;
    } else {
        return NULL;
    }
}

JNIEXPORT jboolean JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_removeData(JNIEnv *env, jobject obj, jint p) {
//    static_data_arr.erase(*point);
    auto iter = static_data_map.find(p);
    if (iter != static_data_map.end()) {
        static_data_map.erase(iter);

        auto iter_en = static_len_map.find(p);
        static_len_map.erase(iter_en);
        return JNI_TRUE;
    } else {
        return JNI_FALSE;
    }
}


JNIEXPORT jint JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_put(JNIEnv *env, jobject obj, jbyteArray data) {
    ++ticket_point;
    char *pCData = to_chars(env, data);
    static_data_map.insert(pair<int, char *>(ticket_point, pCData));
    return ticket_point;
}


JNIEXPORT void JNICALL
Java_com_androidyuan_libcache_fastcache_NativeEntry_release(JNIEnv *env, jobject obj) {
    recycleData(static_len_map);
    recycleData(static_data_map);
}