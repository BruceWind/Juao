package com.androidyuan.libcache.core;

public interface ICacheAssistant {

    boolean put(ITicket ticket);

    ITicket pop(String uuid);

    boolean remove(String uuid);

    boolean hasCached(String key);

    void clearAllCache();

    long getUsageSize();

}
