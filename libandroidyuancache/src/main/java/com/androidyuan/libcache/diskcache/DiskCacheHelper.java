package com.androidyuan.libcache.diskcache;

import com.androidyuan.libcache.core.BaseAssistant;
import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;

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
     * doesn't matter that bytes have been saved.
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
        if (iTicket.getStatus() == TicketStatus.CACHE_STATUS_ONDISK) {
            iTicket.resume();
        }
        return iTicket;
    }

    @Override
    public void clearAllCache() {
        if (mDiskCache != null) {
            Map<String, ITicket> tempCache = new HashMap<>();
            super.moveAll(tempCache);
            for (String key : tempCache.keySet()) {
                try {
                    mDiskCache.remove(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    public boolean put(ITicket ticket) {
        try {
            boolean result = save(ticket.getId(), ticket.toNativeBuffer().array());
            if (result) {
                ticket.onCachedDisk();
            }
            super.put(ticket);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * just record ticket.
     *
     * @param ticket
     * @return
     */
    public boolean onlyPut(ITicket ticket) {
        super.put(ticket);
        return true;
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
    public boolean save(String key, byte[] bytes) throws IOException {

        if (!hasBeenSavedOnDisk(key)) {//if hasnt saved.
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            OutputStream out = editor.newOutputStream(0);
            out.write(bytes);
            out.close();
            editor.commit();
        }
        return true;
    }
}
