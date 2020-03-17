package com.androidyuan.libcache.core;

public interface ITicket {

    String getId();

    void setStatus();

    void getStatus();

    void setNativeAddress(long address);
    int getNativeAddress();


    byte[] getData();

    void emptyData();

    void resume(byte[] bytes);

}
