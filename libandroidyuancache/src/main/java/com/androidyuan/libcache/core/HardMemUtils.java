package com.androidyuan.libcache.core;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class access hardware's info.
 */
public final class HardMemUtils {

    private static final double DEFAULT_USING_SIZE = 500;

    /**
     * get a proper size,I'm going to using the size to set limitation of native cache.
     *
     * If you dont know '/proc/meminfo', pls look at {@link "http://www.programmersought.com/article/58052439487/"}.
     *
     * <p>
     * I dont think that using all of total ram is proper.
     * I will using approximately one half of RAM size.
     * <p>
     * But,I dont think my code is correct.If you dont think so,you can change this method.
     * <p>
     * <p>
     * PS: This method has not been tested on many devices. Take care.
     *
     * @return
     */
    public static double getProperMemorySizeMB() {
        RandomAccessFile reader = null;
        String load;
        double totRam;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }

            totRam = Double.parseDouble(value);

            double mb = totRam / 1024.0;
            return mb * 0.4;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return DEFAULT_USING_SIZE;//return a default value
    }

}
