package com.example.hugestfastestmemorycache;

import android.util.Log;

import com.androidyuan.libcache.nativecache.NativeEntry;

import org.junit.Assert;
import org.junit.Test;

public class JNITest {
    private final int a = 10, b = 11;
    private final String TAG = "JNITest";

    @Test
    public void test() {

        byte[] dataResult = null;
        final byte[] data = {a, b};


        int point = NativeEntry.put(data);

        Log.w(TAG, "put() result : " + point);


        dataResult = NativeEntry.popData(point);
        Assert.assertArrayEquals(dataResult, data);
        Log.w(TAG, "getData() = " + ((int) dataResult[0] + " " + (int) dataResult[1]));


        Assert.assertEquals(NativeEntry.removeData(point), false);
        point = NativeEntry.put(data);
        Assert.assertEquals(NativeEntry.removeData(point), true);

        point = NativeEntry.put(data);
        Assert.assertEquals(NativeEntry.removeData(point), true);
        NativeEntry.release();
        Assert.assertEquals(NativeEntry.removeData(point), false);


    }

}
