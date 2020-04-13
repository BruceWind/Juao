package com.example.cacheexample;

import android.util.Log;

import com.androidyuan.libcache.fastcache.NativeEntry;

import org.junit.Assert;
import org.junit.Test;

public class JNITest {
    private final int a = 10, b = 11;
    private final String TAG = "JNITest";

    @Test
    public void test() {

        byte[] dataResult = null;
        final byte[] data = {a, 0, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, 0, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, 0, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b,
                a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, b, a, 0
        };

        final int len = 2000;
        final int[] pointarr = new int[len];

        for (int i = 0; i < len; i++) {
            pointarr[i] = NativeEntry.put(data);

            Log.w(TAG, "put() result : " + pointarr[i]);
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < len; j++) {

            dataResult = NativeEntry.popData(pointarr[j]);
            Log.w(TAG, "put() result : " + pointarr[j]);

            Assert.assertArrayEquals(dataResult, data);
            Log.w(TAG, "getData() len=" + dataResult.length);
            Log.w(TAG, "getData() = " + ((int) dataResult[dataResult.length - 3] + " " + (int) dataResult[dataResult.length - 1]));

        }


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        Assert.assertEquals(NativeEntry.removeData(point), false);
//        point = NativeEntry.put(data);
//        Assert.assertEquals(NativeEntry.removeData(point), true);
//
//        point = NativeEntry.put(data);
//        Assert.assertEquals(NativeEntry.removeData(point), true);
//        NativeEntry.release();
//        Assert.assertEquals(NativeEntry.removeData(point), false);


    }

}
