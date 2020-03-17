package com.androidyuan.libcache.nativecache;


import com.androidyuan.libcache.core.HardMemUtils;
import com.androidyuan.libcache.core.ICacheHelper;

import java.io.IOException;

public class NativeCacheHelper implements ICacheHelper {

    public NativeCacheHelper(){

        double memorySizeMB = HardMemUtils.getProperMemorySizeMB();
    }

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
