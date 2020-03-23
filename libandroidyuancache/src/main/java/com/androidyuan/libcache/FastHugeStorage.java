package com.androidyuan.libcache;

import android.os.Handler;
import android.os.Looper;

import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;
import com.androidyuan.libcache.core.UUIDHexGenerator;
import com.androidyuan.libcache.diskcache.DiskCacheHelper;
import com.androidyuan.libcache.fastcache.FastLruCacheAssistant;
import com.androidyuan.libcache.fastcache.OnFulledListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The  {@link FastHugeStorage#put(ITicket)} } will return a UUID.It is a String type.
 * When you want to pop data from {@link }FastHugeStorage},you has to call {@link FastHugeStorage#popTicket(String)}.
 */
public class FastHugeStorage implements OnFulledListener {
    private static final String TAG = "StorageManager";

    private static final int COUTN_THREAD = 8;
    private ExecutorService threadPool = Executors.newFixedThreadPool(COUTN_THREAD);

    private final Handler handler = new Handler(Looper.getMainLooper());


    private final UUIDHexGenerator uuidGenerator = new UUIDHexGenerator();

    private DiskCacheHelper diskCacheHelper;
    private FastLruCacheAssistant fastLruCacheAssistant;

    private FastHugeStorage() {
    }

    private static FastHugeStorage sStorage;

    public static FastHugeStorage getInstance() {
        if (sStorage == null) {
            sStorage = new FastHugeStorage();
        }
        return sStorage;
    }

    /**
     * pls set same {@param config} when you call this method everytimes. if you give a different dir,cache file cant clean.
     * pls set your versioncode of app due to that different codes could cause crashes.
     *
     * I don't open about cache size. If you want to know the size,pls look at {@DiskCacheHelper} and {@FastLruCacheAssistant}.
     *
     * @param config
     */
    public void init(CacheConfig config) {
        try {
            fastLruCacheAssistant = new FastLruCacheAssistant(config.getSizeOfMemCache(), this);
            diskCacheHelper = new DiskCacheHelper(config.getDiskDir(), config.getSizeOfDiskCache());

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    diskCacheHelper.clearAllCache();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ticket
     * @return
     */
    public String put(ITicket ticket) {
        ticket.setUuid(uuidGenerator.generate());
        synchronized (this) {
            if (fastLruCacheAssistant.put(ticket)) {
                return ticket.getId();
            }
            if (diskCacheHelper.put(ticket)) {
                return ticket.getId();
            }
            return null;
        }
    }

    /**
     * Might this method spend much time.
     * After you call this,ticket of uuid and its data will be removed.
     *
     * @param uuid
     * @return maybe is null.
     */
    public ITicket popTicket(String uuid) {
        ITicket result = null;
        synchronized (this) {
            if (fastLruCacheAssistant != null && fastLruCacheAssistant.hasCached(uuid)) {
                result = fastLruCacheAssistant.pop(uuid);
                return result;
            }

            if (diskCacheHelper != null && diskCacheHelper.hasCached(uuid)) {
                result = diskCacheHelper.pop(uuid);
                return result;
            }
            return result;
        }
    }

    public void popTicket(final String uuid, final OnPopCompleteListener listener) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                final ITicket ticket = popTicket(uuid);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompleted(ticket);
                    }
                });
            }
        });
    }


    @Override
    public void onMoveToDisk(final ITicket value, final byte[] bytes) {
        if (value == null) return;

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean suc = diskCacheHelper.save(value.getId(), bytes);
                    if (suc) {
                        value.setStatus(TicketStatus.CACHE_STATUS_ONDISK);
                    } else {
                        value.setStatus(TicketStatus.CACHE_STATUS_WAS_LOST);//oooh, but has to put to diskcahce.
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    value.setStatus(TicketStatus.CACHE_STATUS_WAS_LOST);//oooh,
                } finally {
                    diskCacheHelper.onlyPut(value);
                }
            }
        });

    }


}
