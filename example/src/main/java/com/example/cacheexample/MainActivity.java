package com.example.cacheexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidyuan.libcache.CacheConfig;
import com.androidyuan.libcache.FastHugeStorage;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //If you want to set the size,pls look at {@CacheConfig}.
        FastHugeStorage.getInstance().init(new CacheConfig.Builder().setDiskDir(getCacheDir().toString()).build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void testBitmapChache(View v) {
        startActivity(new Intent(this, BitmapActivity.class));
    }

    public void testPacelableCache(View v) {
        startActivity(new Intent(this, ParcelableActivity.class));
    }

    public void testSerilizableCache(View v) {
        startActivity(new Intent(this, SerializableActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FastHugeStorage.getInstance().clear();
    }
}
