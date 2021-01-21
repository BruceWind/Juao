package com.androidyuan.libcache.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;

public class ParcelableUtil {
    public static byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }


    //DirectByteBuffer cant directly to array due to it has offset.
    public static Parcel unmarshall(ByteBuffer buffer, final int len) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(buffer.array(), buffer.arrayOffset(), len);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshall(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}
