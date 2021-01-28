package com.androidyuan.libcache.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;


/**
 * It is transformer to transform object and bytes.
 */
public final class BytesTransform {

    /**
     * DirectByteBuffer -> bytes -> Object
     *
     * @param buffer it is DirectByteBuffer
     * @return
     */
    public static Object bytesToSerializable(ByteBuffer buffer) {
        Object obj = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            if (buffer.isDirect()) {
                byteArrayInputStream = new ByteArrayInputStream(buffer.array(), buffer.arrayOffset(), buffer.position());
            } else {
                byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            }
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }


    /**
     * transfom java object to byte[]
     *
     * @param obj
     * @return
     */
    public static byte[] serializableToBytes(Serializable obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytes;
    }


    public static byte[] marshallParcelable(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static byte[] marshallParcelable(Parcelable parceable, Parcel parcel) {
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unmarshallToParcelable(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }


    //DirectByteBuffer cant directly to array due to it has offset.
    public static Parcel unmarshallToParcelable(ByteBuffer buffer, final int len) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(buffer.array(), buffer.arrayOffset(), len);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    public static <T> T unmarshallToParcelable(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshallToParcelable(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}
