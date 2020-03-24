package com.androidyuan.libcache;

import android.text.TextUtils;

public class CacheConfig {

    public static final long MB = 1024 * 1024;
    private static final long MINIUM_MEM_CACHESIZE_MB = 100 * MB;
    private static final long MINIUM_DISK_CACHESIZE_MB = 100 * MB;

    private long sizeOfMemCache;
    private long sizeOfDiskCache;
    private String diskDir;

    private CacheConfig(long sizeOfMem, long sizeOfDiskm, String dir) {
        this.sizeOfDiskCache = sizeOfDiskm;
        this.sizeOfMemCache = sizeOfMem;
        this.diskDir = dir;
    }

    public long getSizeOfMemCache() {
        return sizeOfMemCache;
    }

    public long getSizeOfDiskCache() {
        return sizeOfDiskCache;
    }

    public String getDiskDir() {
        return diskDir;
    }

    public static final class Builder {

        private long sizeOfMemCache;
        private long sizeOfDiskCache;
        private String diskDir;

        public Builder setSizeOfMemCache(long sizeOfMemCache) {

            if (sizeOfMemCache < MINIUM_MEM_CACHESIZE_MB) {
                throw new IllegalArgumentException("sizeOfMemCache is too small.");
            }
            this.sizeOfMemCache = sizeOfMemCache;
            return this;
        }


        public Builder setSizeOfDiskCache(long sizeOfDiskCache) {
            if (sizeOfDiskCache < MINIUM_DISK_CACHESIZE_MB) {
                throw new IllegalArgumentException("sizeOfDisk is too small.");
            }
            this.sizeOfDiskCache = sizeOfDiskCache;
            return this;
        }


        public Builder setDiskDir(String diskDir) {
            this.diskDir = diskDir;
            return this;
        }


        public CacheConfig build() {
            if (TextUtils.isEmpty(diskDir)) {
                throw new IllegalArgumentException("Reject: You have not set diskDir.Pls set it.");
            }
            if (sizeOfMemCache < MINIUM_MEM_CACHESIZE_MB) {
                sizeOfMemCache = MINIUM_MEM_CACHESIZE_MB;
            }
            if (sizeOfDiskCache < MINIUM_DISK_CACHESIZE_MB) {
                sizeOfDiskCache = MINIUM_DISK_CACHESIZE_MB;
            }
            return new CacheConfig(sizeOfMemCache, sizeOfDiskCache, diskDir);
        }


    }
}
