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

    byte[] toBytes();

    void emptyData();

    void resume();

    void checkStatusMustBe(int status);

    ByteBuffer getBuffer();

    T getBean();

    int getSize();

    void resumeFromDisk(byte[] data);
}
