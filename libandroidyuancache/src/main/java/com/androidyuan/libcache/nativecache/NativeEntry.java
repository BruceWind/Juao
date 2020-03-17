package com.androidyuan.libcache.nativecache;

public final class NativeEntry {

    static {
        System.loadLibrary("cache");
    }

    /**
     * @param data
     * @return point of 'C', reulst is int64 due to point is int64 in 'C'.
     */
    public static native int put(byte[] data);

    /**
     * remove from native.
     * If hasnot been existed in native return false.
     * @param point
     * @return false : maybe has been cleaned or poped.
     */
    public static native boolean removeData(int point);

    /**
     * @param point this prarameter was ceated by  {@NativeEntry#put}.
     * @return
     */
    public static native byte[] popData(int point);

    /**
     * Release all of data from native.
     * After relase all of data,you can put data again.
     */
    public static native void release();

}
