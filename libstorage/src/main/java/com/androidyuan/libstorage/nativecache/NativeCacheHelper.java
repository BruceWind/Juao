package com.androidyuan.libstorage.nativecache;

import com.androidyuan.libstorage.core.ICacheHelper;

import java.io.IOException;

public class NativeCacheHelper implements ICacheHelper {

    @Override
    public boolean save(String key, byte[] bytes) throws IOException {
        return false;
    }

    @Override
    public boolean saveOrUpdate(String key, byte[] bytes) throws IOException {
        return false;
    }

    @Override
    public byte[] read(String key) {
        return new byte[0];
    }

    @Override
    public boolean hasCached(String key) {
        return false;
    }

}
