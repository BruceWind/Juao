package com.example.hugestfastestmemorycache;

import android.util.Log;

import com.androidyuan.libcache.core.UUIDHexGenerator;

import org.junit.Test;

public class UUIDHexGeneratorTest {

    private static final String TAG = "UUIDHexGeneratorTest";

    private final UUIDHexGenerator generator = new UUIDHexGenerator();

    @Test
    public void test() {
        for(int index =0;index<100;index++) {
            Log.w(TAG, generator.generate());
        }

    }


}
