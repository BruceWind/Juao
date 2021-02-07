package com.example.cacheexample;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.androidyuan.libcache.CacheConfig;
import com.androidyuan.libcache.FastHugeStorage;
import com.androidyuan.libcache.data.SerializableTicket;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * testing thread safe.
 */
public class StorageManagerThreadSafeTest {
    private static final String TAG = "StorageManagerThreadSafeTest";

    @Test
    public void test() {
        Log.i(TAG, "start");


        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FastHugeStorage.getInstance().init(new CacheConfig.Builder(appContext).setDiskDir(appContext.getExternalCacheDir().toString()).build());

        //test thread safe in call #put and #pop from multi-thread.
        testPutPopOnAnotherThread();
        testPutPopOnAnotherThread();
        testPutPopOnAnotherThread();
        testPutPopOnAnotherThread();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testPutPop() {

        String uuid = FastHugeStorage.getInstance().put(new SerializableTicket(new ObjectToByteCls()));
        ObjectToByteCls bean = (ObjectToByteCls) FastHugeStorage.getInstance().popTicket(uuid).getBean();
        Assert.assertNotNull(bean);
        Assert.assertEquals(2, bean.i);
        Log.d(TAG, "uuid : " + uuid);
    }

    private void testPutPopOnAnotherThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                int j = 0;
                while (j < 1000) {
                    testPutPop();
                    j++;
                    if (j > 990) {
                        Log.w(TAG, "It is going to end. Current thread ID is " + Thread.currentThread().getId() + ". ");
                    }
                }
                Assert.assertEquals(j, 1000);
            }
        }).start();
    }


    public static class ObjectToByteCls implements Serializable {

        private final String str = "dsadasd";
        private final int i = 2;

    }

}
