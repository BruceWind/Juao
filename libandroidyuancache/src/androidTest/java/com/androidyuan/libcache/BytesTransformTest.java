package com.androidyuan.libcache;

import android.os.Parcel;

import com.androidyuan.libcache.core.BytesTransform;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class BytesTransformTest {
    @Test
    public void test() {

        //test serializable
        final SrlzbTestCls srlbObj = new SrlzbTestCls();
        srlbObj.str = "modifiedText";
        final byte[] originalBytes = BytesTransform.serializableToBytes(srlbObj);
        final SrlzbTestCls input = (SrlzbTestCls) BytesTransform.bytesToSerializable(ByteBuffer.wrap(originalBytes));
        Assert.assertNotNull(input);
        Assert.assertEquals(2, input.i);
        Assert.assertEquals(input.str, "modifiedText");

        //test parcelable
        ParcelableTestCls inputParcelObj = new ParcelableTestCls();

        final byte[] parcelableBytes = BytesTransform.marshallParcelable(inputParcelObj);
        Assert.assertNotNull(parcelableBytes);
        Assert.assertNotEquals(parcelableBytes.length, 0);


        Parcel temp = BytesTransform.unmarshallToParcelable(parcelableBytes);
        Assert.assertNotNull(temp);
        ParcelableTestCls outputParcel = new ParcelableTestCls(temp);
        Assert.assertNotNull(outputParcel);
        Assert.assertTrue(outputParcel.hasBald);


    }


}
