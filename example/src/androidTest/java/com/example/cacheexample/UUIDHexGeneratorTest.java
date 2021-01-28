package com.example.cacheexample;

import com.androidyuan.libcache.core.UUIDHexGenerator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UUIDHexGeneratorTest {

    private static final String TAG = "UUIDHexGeneratorTest";

    private final UUIDHexGenerator generator = new UUIDHexGenerator();

    @Test
    public void test() {
        List<String> uuidList = new ArrayList<>();

        for (int index = 0; index < 10000; index++) {
            uuidList.add(generator.generate());
        }

        for (int i = 0; i < uuidList.size(); i++) {
            for (int j = i + 1; j < uuidList.size(); j++) {
                Assert.assertNotEquals(uuidList.get(i), uuidList.get(j));
            }
        }
    }


}
