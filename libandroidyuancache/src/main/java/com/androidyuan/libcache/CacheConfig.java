package com.androidyuan.libcache;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * This class is suppose to initiate {@link FastHugeStorage}.
 * You can use {@link Builder} to construct {@link CacheConfig} readily.
 */
public final class CacheConfig {

    private static final long MB = 1024 * 1024;
    private static final long MINIUM_MEM_CACHESIZE_MB = 200 * MB;
    private static final long MINIUM_DISK_CACHESIZE_MB = 400 * MB;

    private final long sizeOfMemCache;
    private final long sizeOfDiskCache;
    private final String diskDir;

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
        private final Context context;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder setSizeOfMemCache(long size) {
            this.sizeOfMemCache = size < 1 ? MINIUM_MEM_CACHESIZE_MB : size;
            return this;
        }


        public Builder setSizeOfDiskCache(long size) {
            if (sizeOfDiskCache < MINIUM_DISK_CACHESIZE_MB) {
                throw new IllegalArgumentException("sizeOfDisk is too small.");
            }
            this.sizeOfDiskCache = size;
            return this;
        }


        public Builder setDiskDir(String diskDir) {

            this.diskDir = diskDir;
            return this;
        }

        /**
         * get size of device RAM.
         *
         * @return {long}
         */
        private long getMemorySizeInBytes() {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem;
        }


        public CacheConfig build() {
            if (TextUtils.isEmpty(diskDir)) {
                throw new IllegalArgumentException("Reject: You have not set diskDir.Pls set it.");
            }
            if (sizeOfDiskCache < MINIUM_DISK_CACHESIZE_MB) {
                sizeOfDiskCache = MINIUM_DISK_CACHESIZE_MB;
            }
            if (sizeOfMemCache == 0) {
                sizeOfMemCache = getMemorySizeInBytes() / 4;
            }
            if (TextUtils.isEmpty(diskDir)) {
                throw new IllegalArgumentException("Reject : diskDir is null.");
            } else {
                File file = new File(diskDir);
                if (!file.isDirectory()) {
                    throw new IllegalArgumentException("Reject : It is unexpected value that diskDir is not directory.");
                }
            }
            return new CacheConfig(sizeOfMemCache, sizeOfDiskCache, diskDir);
        }


    }
}
