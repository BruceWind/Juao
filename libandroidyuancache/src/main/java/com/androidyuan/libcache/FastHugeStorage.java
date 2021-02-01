package com.androidyuan.libcache;

import android.os.Handler;
import android.os.Looper;

import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.core.TicketStatus;
import com.androidyuan.libcache.core.UUIDHexGenerator;
import com.androidyuan.libcache.diskcache.DiskCacheHelper;
import com.androidyuan.libcache.fastcache.FastLruCacheAssistant;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The  {@link FastHugeStorage#put(ITicket)} } will return a UUID.It is a String type.
 * When you want to pop data from {@link }FastHugeStorage}, you has to call {@link FastHugeStorage#popTicket(String)}.
 */
public class FastHugeStorage {
    private static final String TAG = "StorageManager";

    private static final int COUTN_THREAD = 8;
    private static final FastHugeStorage sStorage = new FastHugeStorage();
    private final Handler handler = new Handler(Looper.getMainLooper());


    private final UUIDHexGenerator uuidGenerator = new UUIDHexGenerator();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(COUTN_THREAD);
    private DiskCacheHelper diskCacheHelper;
    private FastLruCacheAssistant fastLruCacheAssistant;

    private FastHugeStorage() {
    }

    public static FastHugeStorage getInstance() {
        return sStorage;
    }

    /**
     * pls set same {@param config} when you call this method. If you give a different dir,cache file can't clean.I will clear data when you call {@link FastHugeStorage#init(CacheConfig)}.
     *
     * @param config
     */
    public synchronized void init(CacheConfig config) {
        try {
            fastLruCacheAssistant = new FastLruCacheAssistant(config.getSizeOfMemCache());
            diskCacheHelper = new DiskCacheHelper(config.getDiskDir(), config.getSizeOfDiskCache());
            //clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ticket
     * @return
     */
    public String put(ITicket ticket) {
        checkIfInited();
        ticket.setUuid(uuidGenerator.generate());
        synchronized (this) {
            if (fastLruCacheAssistant.put(ticket)) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        checkIsPoolFulled();
                    }
                });
                return ticket.getId();
            }
            return null;
        }
    }

    private void checkIsPoolFulled() {
        checkIfInited();
        if (fastLruCacheAssistant.getLruCacheStatistics().getUsage() >= fastLruCacheAssistant.getLruCacheStatistics().getLimitation()) {
            final ITicket ticket = fastLruCacheAssistant.poll();
            if (ticket != null) {//really important .
                ticket.setStatus(TicketStatus.CACHE_STATUS_ON_CACHING);//.setStatus(int)' on a null object reference
                diskCacheHelper.put(ticket);
            }
        }
    }

    /**
     * Might this method spend much time due to native poll is full.
     * After you call this,ticket of uuid and its data will be removed.
     *
     * @param uuid
     * @return maybe is null.
     */
    public ITicket popTicket(String uuid) {
        checkIfInited();
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

    /**
     * This method is asynchronous to pop data.Then it will callback call on MainThread.
     *
     * @param uuid
     * @param listener
     */
    public void popTicket(final String uuid, final OnPopCompleteListener listener) {
        checkIfInited();
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


    /**
     * After you call this method,you still can call {@FastHugeStorage#popTicket()}.
     */
    public void clear() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                fastLruCacheAssistant.clearAllCache();
                diskCacheHelper.clearAllCache();
            }
        });
    }

    public void reset() {
        //TODO
    }


    /**
     * @return result size doesn't contain disk cache size.
     */
    public long getMemCacheUsage() {
        checkIfInited();
        return fastLruCacheAssistant.getLruCacheStatistics().getUsage();
    }

    private void checkIfInited(){
        if(fastLruCacheAssistant == null || diskCacheHelper==null) throw new IllegalStateException("Reject:You haven't call FastHugeStorage#init().");
    }

    public long getDiskCacheUsage() {
        return diskCacheHelper.getUsageSize();
    }
}
