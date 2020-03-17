package com.androidyuan.libcache.core;

import java.io.IOException;

public interface ICacheHelper {

    boolean save(String key, byte[] bytes) throws IOException;
    boolean saveOrUpdate(String key, byte[] bytes) throws IOException;
    byte[] read(String key);
    boolean hasCached(String key);
}
