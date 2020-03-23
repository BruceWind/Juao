package com.androidyuan.libcache.fastcache;

public final class NativeEntry {

    static {
        System.loadLibrary("cache");
    }

    /**
     * @param data
     * @return result is a increase number.
     */
    public synchronized static native int put(byte[] data);

    /**
     * remove from native.
     * If hasnot been existed in native return false.
     * pls don't call this method due to that call this method will cause statistic is wrong.
     *
     * @param point
     * @return false : maybe has been cleaned or poped.
     */
    public synchronized static native boolean removeData(int point);

    /**
     * @param point this prarameter was ceated by  {@NativeEntry#put}.
     * @return
     */
    public synchronized static native byte[] popData(int point);

    /**
     * Release all of data from native.
     * After relase all of data,you can put data again.
     */
    public synchronized static native void release();

}
