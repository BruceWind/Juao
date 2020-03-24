package com.androidyuan.libcache.fastcache;


import com.androidyuan.libcache.core.BaseAssistant;
import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class FastLruCacheAssistant extends BaseAssistant {


    private OnFulledListener mOnFulledListener;

    private volatile AbstractQueue<String> queue = null;//this property is used to make lru algorithm.

    private FastLruCacheStatistics lruCacheStatistics = null;

    public FastLruCacheAssistant(long space, final OnFulledListener listener) {
        this.mOnFulledListener = listener;
        queue = new ConcurrentLinkedQueue<String>();
        this.lruCacheStatistics = new FastLruCacheStatistics(space);
    }


    public ITicket pop(final String key) {
        if (queue.contains(key)) {
            //Recently accessed, hence move it to the tail
            queue.remove(key);
            ITicket cacheValue = super.pop(key);
            cacheValue.resume(removeFromNative(cacheValue.getNativeAddress()));
            return cacheValue;
        }
        return null;
    }

    public boolean put(final ITicket value) {
        //ConcurrentHashMap doesn't allow null key or values
        if (value == null || value.getId() == null) throw new NullPointerException();


        if (putToNative(value)) {
            queue.add(value.getId());
            checkIsFulledStack();
            return super.put(value);
        }
        return false;

    }

    private void checkIsFulledStack() {
        if (getLruCacheStatistics().getUsage() >= getLruCacheStatistics().getLimitation()) {
            String lruKey = queue.poll();
            if (lruKey != null) {//really important .
                ITicket outValue = super.pop(lruKey);
                outValue.setStatus(TicketStatus.CACHE_STATUS_ON_CACHING);
                mOnFulledListener.onMoveToDisk(outValue, removeFromNative(outValue.getNativeAddress()));
            }
        }
    }

    @Override
    public void clearAllCache() {
        super.clearAllCache();
        queue.clear();
        NativeEntry.release();
    }

    private byte[] removeFromNative(final int address) {
        byte[] result = NativeEntry.popData(address);
        if (result != null) {
            lruCacheStatistics.onRecycleSpace(result.length);
        }
        return result;
    }


    private boolean putToNative(final ITicket ticket) {
        byte[] data = ticket.getData();
        final int address = NativeEntry.put(data);
        if (address != 0) {
            ticket.onCachedNative(address);
            lruCacheStatistics.onApplySpace(data.length);
        } else {
            return false;
        }
        return true;
    }

    public FastLruCacheStatistics getLruCacheStatistics() {
        return lruCacheStatistics;
    }

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
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        return (stringBuilder.toString());
    }

    public boolean remove(final String uuid) {
        if (queue.contains(uuid)) {
            pop(uuid);
            return true;
        }
        return false;
    }
}