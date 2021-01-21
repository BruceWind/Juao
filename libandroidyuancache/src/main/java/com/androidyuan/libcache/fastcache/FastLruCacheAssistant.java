package com.androidyuan.libcache.fastcache;


import com.androidyuan.libcache.core.BaseAssistant;
import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;

import java.nio.ByteBuffer;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class FastLruCacheAssistant extends BaseAssistant {


    private final OnFulledListener mOnFulledListener;

    private volatile AbstractQueue<String> queue = null;//this property is used to make lru algorithm.

    private FastLruCacheStatistics lruCacheStatistics = null;

    public FastLruCacheAssistant(long space, final OnFulledListener listener) {
        this.mOnFulledListener = listener;
        queue = new ConcurrentLinkedQueue<String>();
        this.lruCacheStatistics = new FastLruCacheStatistics(space);
    }


    public synchronized ITicket pop(final String key) {
        if (queue.contains(key)) {
            //Recently accessed, hence move it to the tail
            queue.remove(key);
            ITicket cacheValue = super.pop(key);
            cacheValue.resume();

            lruCacheStatistics.onRecycleSpace(cacheValue.getSize());
            return cacheValue;
        }
        return null;
    }

    public synchronized boolean put(final ITicket value) {
        //ConcurrentHashMap doesn't allow null key or values
        if (value == null || value.getId() == null) throw new NullPointerException();


        if (putToNative(value)) {
            queue.add(value.getId());
            checkIsFulledStack();

            lruCacheStatistics.onApplySpace(value.getSize());
            return super.put(value);
        }
        return false;

    }

    private void checkIsFulledStack() {
        if (getLruCacheStatistics().getUsage() >= getLruCacheStatistics().getLimitation()) {
            String lruKey = queue.poll();
            if (lruKey != null) {//really important .

                ITicket outValue = super.pop(lruKey);

                lruCacheStatistics.onRecycleSpace(outValue.getSize());

                outValue.setStatus(TicketStatus.CACHE_STATUS_ON_CACHING);//.setStatus(int)' on a null object reference
                mOnFulledListener.onMoveToDisk(outValue);
            }
        }
    }

    @Override
    public synchronized void clearAllCache() {
        super.clearAllCache();
        queue.clear();
//        NativeEntry.release();
    }


    private boolean putToNative(final ITicket ticket) {
        // creating object of ByteBuffer
        // and allocating size capacity
        final ByteBuffer bb = ticket.toNativeBuffer();
        ticket.onCached(bb);
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

    public synchronized boolean remove(final String uuid) {
        if (queue.contains(uuid)) {
            ITicket ticket = pop(uuid);

            if (ticket != null) {
                lruCacheStatistics.onRecycleSpace(ticket.getSize());
            }
            return true;
        }
        return false;
    }
}