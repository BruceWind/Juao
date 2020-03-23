package com.androidyuan.libcache.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidyuan.libcache.core.BaseTicket;
import com.androidyuan.libcache.core.ParcelableUtil;

import java.lang.reflect.Constructor;

public class ParcelTicket extends BaseTicket<Parcelable> {
    Parcelable parcelable;
    Class parcelCls;

    public ParcelTicket(Parcelable p) {
        if (p == null) throw new NullPointerException("There is an unexpected parameter: p.");
        this.parcelable = p;
        parcelCls = p.getClass();
    }

    @Override
    public byte[] getData() {
        return ParcelableUtil.marshall(parcelable);
    }

    @Override
    public void emptyData() {
        parcelable = null;
    }

    @Override
    public void resume(byte[] bytes) {
        Parcel parcel = ParcelableUtil.unmarshall(bytes);

        try {
            Constructor constructor = parcelCls.getDeclaredConstructor(Parcel.class);
            constructor.setAccessible(true);//avoid private constructor.
            parcelable = (Parcelable) constructor.newInstance(parcel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parcelable getBean() {
        return parcelable;
    }
}
