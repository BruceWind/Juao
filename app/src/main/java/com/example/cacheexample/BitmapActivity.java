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
 * You will look a panorama picuture on this activity.I split it to many bitmap and cache them to {@link FastHugeStorage}.
 * <p>
 * In this demo,When ViewHolder of RecyclerView has to show bitmap, the ViewHolder will pop bitmap from  {@link FastHugeStorage}.
 */
public class BitmapActivity extends Activity {


    RecyclerView recyclerView;

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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.panorama);
        final int hei = bitmap.getHeight();
        final int wi = bitmap.getWidth();

        int beginY = 0;
        while (beginY < hei) {
            int cutHei = (hei - beginY) > 100 ? 100 : (hei - beginY);
            Bitmap temp = Bitmap.createBitmap(bitmap, 0, beginY, wi, cutHei);
            list.add(FastHugeStorage.getInstance().put(new BitmapTicket(Bitmap.createBitmap(temp))));
            beginY += (cutHei + 1);
        }

        bitmap.recycle();
        return list;
    }


}
