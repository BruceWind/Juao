package com.example.cacheexample;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.androidyuan.libcache.FastHugeStorage;
import com.androidyuan.libcache.data.SerializableTicket;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

public class StorageManagerTest {
    private static final String TAG = "StorageManagerTest";

    @Test
    public void test() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FastHugeStorage.getInstance().init(appContext.getExternalCacheDir().toString());

        final String uuid = FastHugeStorage.getInstance().put(new SerializableTicket(new ObjectToByteCls()));


        ObjectToByteCls bean = (ObjectToByteCls) FastHugeStorage.getInstance().popTicket(uuid).getBean();
        Log.w(TAG, "bean.i = " + bean.i);
        Log.w(TAG, "bean.str = " + bean.str);
        Assert.assertTrue(bean.i == 2);
    }


    public static class ObjectToByteCls implements Serializable {

        private String str = "dsadasd";
        private int i = 2;

    }

}
