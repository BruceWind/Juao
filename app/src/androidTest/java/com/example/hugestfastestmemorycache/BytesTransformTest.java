package com.example.hugestfastestmemorycache;

import android.content.Context;

import com.androidyuan.libstorage.diskcache.DiskCahcheHelper;
import com.androidyuan.libstorage.diskcache.DiskLruCache;
import com.androidyuan.libstorage.core.BytesTransform;

import org.junit.Assert;
import org.junit.Test;
import java.io.Serializable;

import androidx.test.platform.app.InstrumentationRegistry;

public class BytesTransformTest {
    private static final String TAG = "NativeCacheEntryTest";

    private DiskLruCache mDiskCache;

    private DiskCahcheHelper mDiskHelper;

    @Test
    public void name() {

        //--------------  test transform: ---------
        ObjectToByteCls obj = (ObjectToByteCls) BytesTransform.bytesToSerializable(BytesTransform.serializableToBytes(new ObjectToByteCls()));
        Assert.assertTrue(obj != null);
        Assert.assertTrue(obj.i == 2);

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();


        //--------------  test DiskCahcheHelper: ---------
        try {
            mDiskHelper = new DiskCahcheHelper(appContext.getExternalCacheDir().toString());

            final String key = "sasdfadgfd";
            boolean value = mDiskHelper.save(key, BytesTransform.serializableToBytes(obj));
            Assert.assertTrue(value);
            obj = (ObjectToByteCls) BytesTransform.bytesToSerializable(mDiskHelper.read(key));


            Assert.assertTrue(obj != null);
            Assert.assertTrue(obj.i == 2);

            Assert.assertTrue(mDiskHelper.remove(key));
            Assert.assertTrue(!(mDiskHelper.remove(key + "asdasd")));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertTrue(false);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static class ObjectToByteCls implements Serializable {

        private String str = "dsadasd";
        private int i = 2;

    }
}
