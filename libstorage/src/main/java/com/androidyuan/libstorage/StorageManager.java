package com.androidyuan.libstorage;

import com.androidyuan.libstorage.core.UUIDHexGenerator;

public class StorageManager {

    private static final String TAG = "StorageManager";

    private final UUIDHexGenerator generator = new UUIDHexGenerator();

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
     * pls set your versioncode of app due to that different codes could cause crashes.
     *
     * @param dir
     * @param appVersionCode
     */
    public void init(String dir, int appVersionCode) {

    }

    /**
     *
     * @return ticket : if can't save,you will get a null.
     */
    public String save(){
        return "";
    }


}
