package com.androidyuan.libstorage;

public class StorageManager {

    private StorageManager() {
    }

    private static StorageManager storageManager;

    public static StorageManager getInstance() {
        if (storageManager == null) {
            storageManager = new StorageManager();
        }
        return storageManager;
    }

    /**
     * pls set same {@param dir} when you call this method everytimes. if you give a different dir,cache file cant clean.
     * pls set your versioncode of app due to difference codes could cause crashes.
     *
     * @param dir
     * @param appVersionCode
     */
    public void init(String dir, int appVersionCode) {

    }


}
