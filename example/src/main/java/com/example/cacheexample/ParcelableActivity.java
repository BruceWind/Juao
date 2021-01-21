package com.example.cacheexample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidyuan.libcache.FastHugeStorage;
import com.androidyuan.libcache.data.ParcelTicket;
import com.example.cacheexample.bean.ParceEngineer;

/**
 * This class  is used to test caching {@link ParcelTicket}.
 */
public class ParcelableActivity extends Activity {

    private String uuid;
    private TextView textView;

    @Override
    public void onCreate(Bundle save) {
        super.onCreate(save);

        setContentView(R.layout.activity_test_parcelable_cache);
        textView = findViewById(R.id.text);

    }


    public void onClickPut(View view) {
        textView.setTextColor(Color.LTGRAY);
        uuid = FastHugeStorage.getInstance().put(new ParcelTicket(new ParceEngineer()));
        textView.setText("put Engineer result :\n " + uuid);
    }

    public void onClickPop(View view) {
        if (uuid != null) {
            ParceEngineer engineer = (ParceEngineer) FastHugeStorage.getInstance().popTicket(uuid).getBean();
            textView.setText("pop Engineer from cache : \n" + engineer);
            textView.setTextColor(Color.BLACK);
            uuid = null;//You has to empty uuid.
        } else {
            textView.setText("pls click put ben before.");
            textView.setTextColor(Color.RED);
        }
    }
}
