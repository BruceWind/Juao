package com.androidyuan.libcache.core;

import java.nio.ByteBuffer;

public abstract class BaseTicket<T> implements ITicket<T> {

    protected ByteBuffer buffer;
    private String uuid = null;
    private volatile int status;

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setUuid(String id) {
        uuid = id;
    }

    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public void onCachedDisk() {
        setStatus(TicketStatus.CACHE_STATUS_ONDISK);
        buffer.clear();
        buffer = null;
        emptyData();
    }

    @Override
    public void checkStatusMustBe(int status) {
        if (this.status != status) {
            throw new IllegalStateException(String.format("Ticket status was not %d.", status));
        }
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int s) {
        status = s;
    }

    @Override
    public void onCached(ByteBuffer b) {
        this.buffer = b;
        emptyData();
        setStatus(TicketStatus.CACHE_STATUS_ON_NATIVE);
    }

}
