package com.androidyuan.libcache.fastcache;

import java.util.concurrent.atomic.AtomicLong;

/*
 */
public class FastLruCacheStatistics {
    private final long limitationSize;
    private final AtomicLong putCount = new AtomicLong();
    private volatile long usageSpace = 0;

    /**
     * Create a new statistics object with a particular size that should match
     * the size of the cache
     *
     * @param spaceSize the size of the cache space
     */
    public FastLruCacheStatistics(long spaceSize) {
        this.limitationSize = spaceSize;
    }

    /**
     * Get the size of the cache
     *
     * @return the size of the cache
     */
    public long getLimitation() {
        return this.limitationSize;
    }


    public long getUsage() {
        return this.usageSpace;
    }

    /**
     * Increment the number of 'put' requests
     */
    private void incrementPutCount() {
        this.putCount.incrementAndGet();
    }

    private void decrementPutCount() {
        this.putCount.decrementAndGet();
    }

    public void onApplySpace(final long space) {
        if (space < 1) throw new IllegalArgumentException("'space' is a wrong param.");

        this.usageSpace += space;
        incrementPutCount();
    }

    public void onRecycleSpace(final long space) {

        if (space < 1) throw new IllegalArgumentException("'space' is a wrong param.");
        usageSpace -= space;
        if (usageSpace < 0) throw new IllegalArgumentException("There is dumplication calls.");

        decrementPutCount();
    }

    public void reset() {
        usageSpace = 0;
    }
}