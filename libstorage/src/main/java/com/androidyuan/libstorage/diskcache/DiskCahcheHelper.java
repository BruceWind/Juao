package com.androidyuan.libstorage.diskcache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskCahcheHelper {

    private DiskLruCache mDiskCache;

    public DiskCahcheHelper(String url) throws IOException {
        mDiskCache = DiskLruCache.open(new File(url), 1, 1, 600 * 1024);
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

        if (!hasSaved(key)) {//if hasnt saved.
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            OutputStream out = editor.newOutputStream(0);
            out.write(bytes);
            out.close();
            editor.commit();
            return true;
        }

        return false;
    }

    /**
     * doesn't matter that bytes have been saved.
     *
     * @param key
     * @param bytes
     * @return
     * @throws IOException
     */
    public boolean saveOrUpdate(String key, byte[] bytes) throws IOException {

        if (mDiskCache != null) {
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            OutputStream out = editor.newOutputStream(0);
            out.write(bytes);
            out.close();
            editor.commit();
            return true;
        }

        return false;
    }


    /**
     * read bytes from diskcache.
     *
     * @param key
     * @return bytes
     */
    public byte[] read(String key) throws IOException {

        byte[] bytes = null;
        if (hasSaved(key)) {
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            InputStream inputStream = editor.newInputStream(0);
            if (inputStream == null) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int len = 0;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                bytes = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(editor !=null) {
                    editor.commit();
                }
            }

        }
        return bytes;
    }

    public boolean hasSaved(String key) throws IOException {
        if (mDiskCache != null) {
            return mDiskCache.get(key) != null;
        }
        return false;
    }


    public boolean remove(String key) throws IOException {
        return mDiskCache != null && mDiskCache.remove(key);
    }



}
