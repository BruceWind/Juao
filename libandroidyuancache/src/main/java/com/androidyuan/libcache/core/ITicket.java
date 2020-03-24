package com.androidyuan.libcache.core;

public interface ITicket<T> {

    String getId();

    void setUuid(String uuid);

    void onCachedDisk();

    int getStatus();

    void setStatus(int status);

    void onCachedNative(int address);

    int getNativeAddress();


    byte[] getData();

    void emptyData();

    void resume(byte[] bytes);


    T getBean();
}
