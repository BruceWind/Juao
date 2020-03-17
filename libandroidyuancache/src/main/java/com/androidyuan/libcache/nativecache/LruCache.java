package com.androidyuan.libcache.nativecache;


import com.androidyuan.libcache.core.ITicket;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class LruCache{

    private OnFulledListener mOnFulledListener;

    private Map<String, ITicket> cache = null;
    private AbstractQueue<String> queue = null;
    private LruCacheStatistics lruCacheStatistics = null;


   
    public LruCache(long space,OnFulledListener listener) {
        this.mOnFulledListener = listener;
        cache = new ConcurrentHashMap<String, ITicket>();
        queue = new ConcurrentLinkedQueue<String>();

        this.lruCacheStatistics = new LruCacheStatistics(space);
    }

    public boolean containsKey(String key) {
        return(cache.containsKey(key));
    }


    public ITicket get(String key) {
        //Recently accessed, hence move it to the tail
        queue.remove(key);
        ITicket cacheValue = cache.remove(key);
        cacheValue.resume(removeFromNative(cacheValue.getNativeAddress()));
        return cacheValue;
    }

    public ITicket getSilent(String key) {
        return cache.get(key);
    }

    public void put(String key, ITicket value) {
        //ConcurrentHashMap doesn't allow null key or values
        if(key == null || value == null) throw new NullPointerException();

        if(cache.containsKey(key)) {
            queue.remove(key);
        }

        if(getLruCacheStatistics().getUsage() >= getLruCacheStatistics().getLimitation()) {
            String lruKey = queue.poll();
            if(lruKey != null) {
                ITicket outValue = cache.remove(lruKey);
                mOnFulledListener.onNeedRomove(outValue,removeFromNative(outValue.getNativeAddress()));
            }
        }

        queue.add(key);
        cache.put(key,value);
        putToNative(value);
        putToNative(value);

    }




    //TODO

    public void cleanAllCache(){
        cache.clear();
        queue.clear();
        //TODO clean from native.

    }


    //TODO
    private boolean removeFromNative(ITicket value){
        //if cache on native.
        NativeEntry.removeData(value.getNativeAddress());
        //else if cache on disk.
        return true;
    }

    private byte[] removeFromNative(int address){
        NativeEntry.popData(address);
        return null;
    }

    //TODO
    private boolean putToNative(ITicket ticket){
        ticket.emptyData();
        ticket.setNativeAddress(NativeEntry.put(ticket.getData()));
        return true;
    }




    public ITicket getLeastRecentlyUsed() {
        String remove = queue.remove();
        queue.add(remove);
        return(cache.get(remove));
    }


    public LruCacheStatistics getLruCacheStatistics() { return lruCacheStatistics; }

    @Override
    public synchronized String toString() {
        Iterator<String> iterator = queue.iterator();
        StringBuilder stringBuilder = new StringBuilder();

        while (iterator.hasNext()) {
            String key = iterator.next();
            stringBuilder.append("{ ");
            stringBuilder.append(key);
            stringBuilder.append(":");
            stringBuilder.append(this.getSilent(key));
            stringBuilder.append(" }");
            if(iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        return(stringBuilder.toString());
    }
}