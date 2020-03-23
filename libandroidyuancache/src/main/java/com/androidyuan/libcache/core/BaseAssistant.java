package com.androidyuan.libcache.core;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseAssistant implements ICacheAssistant {

    //this Map is not important,maybe i will remove it.
    private volatile Map<String, ITicket> cache = new ConcurrentHashMap<String, ITicket>();

    /**
     * only check from HashMap.
     *
     * @param key
     * @return
     */
    public synchronized boolean hasCached(String key) {
        return cache.containsKey(key);
    }


    /**
     * may return null.
     *
     * @param key
     * @return
     */
    public synchronized ITicket getSilent(String key) {
        return cache.get(key);
    }


    public boolean put(ITicket value) {
        //ConcurrentHashMap doesn't allow null key or values
        if (value == null || value.getId() == null) throw new NullPointerException();
        cache.put(value.getId(), value);

        return true;
    }


    public synchronized ITicket pop(String uuid) {
        return cache.remove(uuid);
    }

    public synchronized void clearAllCache() {
        cache.clear();
    }

}
