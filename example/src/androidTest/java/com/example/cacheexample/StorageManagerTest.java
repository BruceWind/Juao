package com.example.cacheexample;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.androidyuan.libcache.CacheConfig;
import com.androidyuan.libcache.FastHugeStorage;

import org.junit.Test;

import java.io.Serializable;

public class StorageManagerTest {
    private static final String TAG = "StorageManagerTest";

    @Test
    public void test() {
        Log.i(TAG, "start");


        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FastHugeStorage.getInstance().init(new CacheConfig.Builder().setDiskDir(appContext.getExternalCacheDir().toString()).build());

        new Thread(new Runnable() {
            @Override
            public void run() {

                int j = 0;
                while (j < 1000) {
                    testPutPop();
                    j++;
                }

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                int j = 0;
                while (j < 1000) {
                    testPutPop();
                    j++;
                }

            }
        });

        int i = 0;
        while (i < 1000) {
            testPutPop();
            i++;
        }
    }

    private void testPutPop() {
        //TODO
//        String uuid = FastHugeStorage.getInstance().put(new SerializableTicket(new ObjectToByteCls()));
//        ObjectToByteCls bean = (ObjectToByteCls) FastHugeStorage.getInstance().popTicket(uuid).getBean();
//        Assert.assertEquals(2, bean.i);
//        Log.d(TAG, "uudi : " + uuid);
    }


    public static class ObjectToByteCls implements Serializable {

        private final String str = "dsadasd";
        private final int i = 2;

    }

}
