package com.androidyuan.libcache.diskcache;

import com.androidyuan.libcache.core.BaseAssistant;
import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;
import com.androidyuan.libcache.data.BitmapTicket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class DiskCacheHelper extends BaseAssistant {

    private final DiskLruCache mDiskCache;

    public DiskCacheHelper(String url, long size) throws IOException {
        mDiskCache = DiskLruCache.open(new File(url), 1, 1, size);
    }


    /**
     * doesn't matter that bytes have been rewrite.
     *
     * @param key
     * @param bytes
     * @return
     * @throws IOException
     */
//    public boolean saveOrUpdate(String key, byte[] bytes) throws IOException {
//
//        if (mDiskCache != null) {
//            DiskLruCache.Editor editor = mDiskCache.edit(key);
//            OutputStream out = editor.newOutputStream(0);
//            out.write(bytes);
//            out.close();
//            editor.commit();
//            return true;
//        }
//
//        return false;
//    }


    /**
     * read bytes from diskcache.
     *
     * @param key
     * @return bytes
     */
    private byte[] read(String key) {

        byte[] bytes = null;
        DiskLruCache.Editor editor = null;
        InputStream inputStream = null;
        try {
            editor = mDiskCache.edit(key);
            inputStream = editor.newInputStream(0);
            if (inputStream == null) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int len = 0;

            while ((len = inputStream.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            bytes = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (editor != null) {
                try {
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    public boolean hasCached(String key) {
        if (!super.hasCached(key)) return false;


        return hasBeenSavedOnDisk(key);
    }

    private boolean hasBeenSavedOnDisk(String key) {
        if (mDiskCache != null) {
            try {
                return mDiskCache.get(key) != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public ITicket pop(String uuid) {
        ITicket iTicket = super.pop(uuid);
        iTicket.checkStatusMustBe(TicketStatus.CACHE_STATUS_ONDISK);
        iTicket.resumeFromDisk(read(uuid));
        iTicket.setStatus(TicketStatus.CACHE_STATUS_IS_RESUMED);
        return iTicket;
    }

    @Override
    public void clearAllCache() {
        if (mDiskCache != null) {
            Map<String, ITicket> tempCache = new HashMap<>();
            moveAll(tempCache);
            super.clearAllCache();
            for (String key : tempCache.keySet()) {
                try {
                    tempCache.get(key).setStatus(TicketStatus.CACHE_STATUS_HAS_RELEASED);
                    mDiskCache.remove(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public long getUsageSize() {
        return mDiskCache != null ? mDiskCache.size() : 0;
    }


    @Override
    public boolean put(ITicket value) {

        try {

            boolean suc = true;
            if (value.getBuffer().isReadOnly()) {
                suc = false;
            } else if (value.getBuffer().isDirect() && !(value instanceof BitmapTicket)) {
//                byte[] byteArray = new byte[value.getSize()];
//                value.getBuffer().get(byteArray, value.getBuffer().arrayOffset(), value.getSize());
//                save(value.getId(), byteArray);

                save(value.getId(), value.toBytes());
                value.getBuffer().clear();
            } else {
                save(value.getId(), value.getBuffer().array());
                value.getBuffer().clear();
            }
            if (suc) {
                value.onCachedDisk();
                value.setStatus(TicketStatus.CACHE_STATUS_ONDISK);
            } else {
                value.setStatus(TicketStatus.CACHE_STATUS_WAS_LOST);
            }
            return suc && super.put(value);
        } catch (IOException e) {
            e.printStackTrace();
            value.setStatus(TicketStatus.CACHE_STATUS_WAS_LOST);
        } finally {
        }
        return false;
    }



    @Override
    public boolean remove(String uuid) {
        if (hasCached(uuid)) {
            super.pop(uuid);
            try {
                mDiskCache.remove(uuid);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    /**
     * won't save repeatly.
     *
     * @param key
     * @param bytes
     * @return
     * @throws IOException
     */
    private boolean save(String key, byte[] bytes) throws IOException {

        if (!hasBeenSavedOnDisk(key)) {//if hasnt saved.
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            OutputStream out = editor.newOutputStream(0);
            out.write(bytes);
            out.close();
            editor.commit();
        }
        return true;
    }

    public void deleteAll() {
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
