package com.androidyuan.libcache.core;

public abstract class BaseTicket<T> implements ITicket<T> {

    private String uuid = null;//
    private int address;// native ticket
    private int status;


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
        address = 0;
        emptyData();
    }

    @Override
    public void setStatus(int s) {
        status = s;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void onCachedNative(int address) {
        this.address = address;
        emptyData();
        setStatus(TicketStatus.CACHE_STATUS_ON_NATIVE);
    }

    @Override
    public int getNativeAddress() {
        return address;
    }


}
