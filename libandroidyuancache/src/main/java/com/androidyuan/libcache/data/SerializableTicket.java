package com.androidyuan.libcache.data;

import com.androidyuan.libcache.core.BaseTicket;
import com.androidyuan.libcache.core.BytesTransform;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializableTicket extends BaseTicket<Serializable> {

    private final int size;
    private Serializable serializable;
    private byte[] data;

    public SerializableTicket(Serializable srlzb) {
        if (srlzb == null)
            throw new NullPointerException("There is an unexpected parameter: srlzb.");
        serializable = srlzb;
        data = BytesTransform.serializableToBytes(serializable);
        size = data.length;
    }

    @Override
    public ByteBuffer toNativeBuffer() {
        ByteBuffer temp = ByteBuffer.allocateDirect(data.length);
        temp.put(data);
        data = null;
        return temp;
    }

    @Override
    public void emptyData() {
        serializable = null;
    }

    @Override
    public void resume() {
        serializable = (Serializable) BytesTransform.bytesToSerializable(buffer);
        buffer.clear();
    }

    @Override
    public Serializable getBean() {
        return serializable;
    }

    @Override
    public int getSize() {
        return size;
    }
}
