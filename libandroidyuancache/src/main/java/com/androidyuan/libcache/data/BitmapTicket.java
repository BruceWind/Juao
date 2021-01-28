package com.androidyuan.libcache.data;

import android.graphics.Bitmap;

import com.androidyuan.libcache.core.BaseTicket;
import com.androidyuan.libcache.core.TicketStatus;

import java.nio.ByteBuffer;

/**
 * After you put this bitmap ,you can't use it due to it is recycled.
 * Device version Higher than 8.0 that don't has to use this class,because bitmap will storage on native memory on higher device.
 * <p>
 * Remind:
 */
public class BitmapTicket extends BaseTicket<Bitmap> {

    private final int width;
    private final Bitmap.Config config;
    private final int size;
    private int height = 0;
    private volatile Bitmap bitmap;


    public BitmapTicket(Bitmap bitmap) {
        if (bitmap == null)
            throw new NullPointerException("There is an unexpected parameter: bitmap.");

        this.bitmap = bitmap;

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        config = bitmap.getConfig();
        size = bitmap.getByteCount();
    }

    /**
     * If you call a data
     *
     * @return
     */
    @Override
    public ByteBuffer toNativeBuffer() {
        if (bitmap.isRecycled())
            throw new IllegalArgumentException("Can't call toNativeBuffer() after was emptied;");


        ByteBuffer buf = ByteBuffer.allocateDirect(size);
        synchronized (this) {
            bitmap.copyPixelsToBuffer(buf);
        }
        return buf;
    }

    @Override
    public byte[] toBytes() {
        return toNativeBuffer().array();
    }

    @Override
    public void setStatus(int s) {
        super.setStatus(s);
        if (s == TicketStatus.CACHE_STATUS_WAS_LOST) {
            bitmap = null;
        }
    }

    /**
     * Former bitmap cant be used again,after call this method.
     */
    @Override
    public void emptyData() {
        synchronized (this) {
            bitmap.recycle();//after empty data,memory is clear. this bitmap can't be used again.
            bitmap = null;//doesnt matter.
        }
    }

    @Override
    public void resume() {
        synchronized (this) {
            bitmap = Bitmap.createBitmap(width, height, config);
            byte[] bytes = buffer.array();
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytes));//cant make buffer directly being method param,
            buffer.clear();
        }
    }


    @Override
    public Bitmap getBean() {
        if (bitmap != null && bitmap.isRecycled()) {//something wrong.
            throw new IllegalArgumentException("It was being recycled.");
        }
        return bitmap;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void resumeFromDisk(byte[] read) {
        bitmap = Bitmap.createBitmap(width, height, config);
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(read));//resume data
    }
}
