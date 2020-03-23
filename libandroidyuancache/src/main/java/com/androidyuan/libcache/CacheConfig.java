package com.androidyuan.libcache;

import com.androidyuan.libcache.core.HardMemUtils;

public class CacheConfig {

    private long sizeOfMemCache;
    private long sizeOfDiskCache;
    private String diskDir;

    public long getSizeOfMemCache() {
        return sizeOfMemCache;
    }

    public long getSizeOfDiskCache() {
        return sizeOfDiskCache;
    }

    public String getDiskDir() {
        return diskDir;
    }

    private CacheConfig(long sizeOfMem, long sizeOfDiskm, String dir) {
        this.sizeOfDiskCache = sizeOfDiskm;
        this.sizeOfMemCache = sizeOfMem;
        this.diskDir = dir;
    }


    public static final class Builder {
        private long sizeOfMemCache;
        private long sizeOfDiskCache;
        private String diskDir;

        public long getSizeOfMemCache() {
            return sizeOfMemCache;
        }

        public Builder setSizeOfMemCache(long sizeOfMemCache) {
            this.sizeOfMemCache = sizeOfMemCache;
            return this;
        }

        public long getSizeOfDiskCache() {
            return sizeOfDiskCache;
        }

        public Builder setSizeOfDiskCache(long sizeOfDiskCache) {
            this.sizeOfDiskCache = sizeOfDiskCache;
            return this;
        }

        public String getDiskDir() {
            return diskDir;
        }

        public Builder setDiskDir(String diskDir) {
            this.diskDir = diskDir;
            return this;
        }


        public CacheConfig build() {
            if (sizeOfMemCache < 1) {
                double memorySizeMB = HardMemUtils.getProperMemorySizeMB();
                sizeOfMemCache = (long) memorySizeMB * 1024 * 1024;
            }
            if (sizeOfDiskCache < 1) {
                sizeOfDiskCache = 1024 * 1024 * 1024;
            }
            return new CacheConfig(sizeOfMemCache, sizeOfDiskCache, diskDir);
        }


    }
}
