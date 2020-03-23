package com.androidyuan.libcache.fastcache;

/*
 */
public class FastLruCacheStatistics {
    private long limitSize = 0;
    private long usageSpace = 0;
    private int putCount = 0;

    /**
     * Create a new statistics object with a particular size that should match
     * the size of the cache
     *
     * @param spaceSize the size of the cache space
     */
    public FastLruCacheStatistics(long spaceSize) {
        this.limitSize = spaceSize;
    }

    /**
     * Get the size of the cache
     *
     * @return the size of the cache
     */
    public long getLimitation() {
        return this.limitSize;
    }


    public long getUsage() {
        return this.usageSpace;
    }

    /**
     * Increment the number of 'put' requests
     */
    private void incrementPutCount() {
        this.putCount++;
    }

    private void decrementPutCount() {
        this.putCount--;
    }

    public void onApplySpace(final long space) {
        if (space < 1) throw new IllegalArgumentException("'space' is a wrong param.");

        this.usageSpace += space;
        incrementPutCount();
    }

    public void onRecycleSpace(final long space) {

        if (space < 1) throw new IllegalArgumentException("'space' is a wrong param.");

        this.usageSpace -= space;

        if (usageSpace < 0) throw new IllegalArgumentException("There is dumplication calls.");

        decrementPutCount();
    }

}