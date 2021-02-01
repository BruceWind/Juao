package com.androidyuan.libcache;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is suppose to test {@link CacheConfig} and  {@link CacheConfig.Builder}.
 * test cases:
 * 1.whether {@link CacheConfig.Builder#getDiskDir()} is available}.
 * 2.whether {@link CacheConfig.Builder#getSizeOfMemCache() is available}.
 * 3.whether {@link CacheConfig.Builder#getSizeOfDiskCache() is available}.
 *
 */
public class CacheConfigTest {

    @Test
    public void test() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            CacheConfig.Builder forgottenSetDirbuilder = new CacheConfig.Builder(appContext);
            forgottenSetDirbuilder.build();//this line is suppose to throw IllegalArgumentException.
            Assert.fail("Builder.build() is expected to throw IllegalArgumentException, but it has not thrown.");
        } catch (IllegalArgumentException ex) {
            //it pass.
        }
        try {
            final String unexpectedFileName = appContext.getCacheDir().getAbsolutePath() + "test.jpg";
            CacheConfig.Builder builderDirIsUnexpected = new CacheConfig.Builder(appContext).setDiskDir(unexpectedFileName);
            builderDirIsUnexpected.build();//this line is suppose to throw IllegalArgumentException.
            Assert.fail("Builder.build() is expected to throw IllegalArgumentException, but it has not thrown.");
        } catch (IllegalArgumentException ex) {
            //it pass.
        }

        CacheConfig.Builder builder = new CacheConfig.Builder(appContext).setDiskDir(appContext.getCacheDir().getAbsolutePath());
        CacheConfig config = builder.build();
        Assert.assertTrue(config.getSizeOfDiskCache() > 0);
        Assert.assertTrue(config.getSizeOfMemCache() > 0);

    }
}
