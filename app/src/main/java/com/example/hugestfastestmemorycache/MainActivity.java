package com.example.hugestfastestmemorycache;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        Log.i("XXXXA","availableMegs : "+getMemorySizeHumanizedMB());
    }

    public double getMemorySizeHumanizedMB() {
        RandomAccessFile reader;
        String load;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam;
        String lastValue = "";

        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }

            reader.close();

            totRam = Double.parseDouble(value);

            double mb = totRam / 1024.0;
            return mb;
//            double gb = totRam / 1048576.0;
//            double tb = totRam / 1073741824.0;
//
//            if (tb > 1) {
//                lastValue = twoDecimalForm.format(tb).concat(" TB");
//            } else if (gb > 1) {
//                lastValue = twoDecimalForm.format(gb).concat(" GB");
//            } else if (mb > 1) {
//                lastValue = twoDecimalForm.format(mb).concat(" MB");
//            } else {
//                lastValue = twoDecimalForm.format(totRam).concat(" KB");
//            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0d;
    }
}
