package com.androidyuan.libcache.core;

import java.nio.ByteBuffer;

public interface ITicket<T> {

    String getId();

    void setUuid(String uuid);

    void onCachedDisk();

    int getStatus();

    void setStatus(int status);

    void onCached(ByteBuffer buffer);

    ByteBuffer toNativeBuffer();

    void emptyData();

    void resume();

    ByteBuffer getBuffer();

    T getBean();

    int getSize();
}
