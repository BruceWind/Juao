package com.androidyuan.libcache.data;

import com.androidyuan.libcache.core.BaseTicket;
import com.androidyuan.libcache.core.BytesTransform;

import java.io.Serializable;

public class SerializableTicket extends BaseTicket<Serializable> {

    Serializable serializable;

    public SerializableTicket(Serializable srlzb) {
        if (srlzb == null)
            throw new NullPointerException("There is an unexpected parameter: srlzb.");
        serializable = srlzb;
    }

    @Override
    public byte[] getData() {
        return BytesTransform.serializableToBytes(serializable);
    }

    @Override
    public void emptyData() {
        serializable = null;
    }

    @Override
    public void resume(byte[] bytes) {
        serializable = (Serializable) BytesTransform.bytesToSerializable(bytes);
    }

    @Override
    public Serializable getBean() {
        return serializable;
    }
}
