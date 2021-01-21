package com.example.cacheexample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidyuan.libcache.FastHugeStorage;
import com.androidyuan.libcache.data.BitmapTicket;

import java.util.ArrayList;
import java.util.List;

/**
 * This class  is used to test caching {@link BitmapTicket}.
 * You will look a panorama picuture on this activity.
 * But it is not single bitmap,I split it to many bitmap and cache them to {@link FastHugeStorage}.
 * <p>
 * In this demo,When ViewHolder of RecyclerView is going to show bitmap, the ViewHolder need pop bitmap from  {@link FastHugeStorage}.
 */
public class BitmapActivity extends Activity {


    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle save) {
        super.onCreate(save);

        setContentView(R.layout.activity_test_bitmap_cache);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BitmapAdapter(getTicketIds(), (TextView) findViewById(R.id.txt_cache_size)));

    }


    /**
     * if you run this method in 1080*1920 device.It will split R.mipmap.panorama to 76 bitmaps.
     *
     * @return too many uuid insert to a list.  Maybe size of list is 76.
     */
    private List<String> getTicketIds() {
        List<String> list = new ArrayList<>();
        int[] resArr = {R.mipmap.panorama_1,
                R.mipmap.panorama_2,
                R.mipmap.panorama_3,
                R.mipmap.panorama_4,
                R.mipmap.panorama_5,
                R.mipmap.panorama_6,
                R.mipmap.panorama_7,
                R.mipmap.panorama_8,
                R.mipmap.panorama_9,
                R.mipmap.panorama_10,
                R.mipmap.panorama_11,
                R.mipmap.panorama_12,
                R.mipmap.panorama_13,
                R.mipmap.panorama_14,
                R.mipmap.panorama_15,
                R.mipmap.panorama_16,
                R.mipmap.panorama_17,
                R.mipmap.panorama_18
        };

        for (int i : resArr) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
            list.add(FastHugeStorage.getInstance().put(new BitmapTicket(bitmap)));
            bitmap.recycle();
        }
        return list;
    }





}
