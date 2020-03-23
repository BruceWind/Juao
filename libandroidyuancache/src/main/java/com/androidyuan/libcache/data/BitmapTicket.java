package com.androidyuan.libcache.data;

import android.graphics.Bitmap;

import com.androidyuan.libcache.core.BaseTicket;
import com.androidyuan.libcache.core.TicketStatus;

import java.nio.ByteBuffer;

/**
 * After you put this bitmap ,you can't use it due to it is recycled.
 * Device version Higher than 8.0 that don't has to use this class,because bitmap will storage on native memory on higher device.
 *
 * Remind:
 */
public class BitmapTicket extends BaseTicket<Bitmap> {

    private int width, height = 0;
    private Bitmap.Config config;

    private Bitmap bitmap;


    public BitmapTicket(Bitmap bitmap) {
        if (bitmap == null)
            throw new NullPointerException("There is an unexpected parameter: bitmap.");

        this.bitmap = bitmap;

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        config = bitmap.getConfig();
    }

    /**
     * If you call a data
     *
     * @return
     */
    @Override
    public byte[] getData() {
        if (bitmap.isRecycled())
            throw new IllegalArgumentException("Can't call getData() after was emptied;");

        int bytes = bitmap.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bitmap.copyPixelsToBuffer(buf);
        return buf.array();
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
        bitmap.recycle();//after empty data,memory is clear. this bitmap can't be used again.
        bitmap = null;//doesnt matter.
    }

    @Override
    public void resume(byte[] bytes) {
        if (bytes != null) {
            bitmap = Bitmap.createBitmap(width, height, config);
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytes));//resume data
        }
    }


    @Override
    public Bitmap getBean() {
        if (bitmap != null && bitmap.isRecycled()) {//something wrong.
            throw new IllegalArgumentException("It was being recycled.");
        }
        return bitmap;
    }
}
