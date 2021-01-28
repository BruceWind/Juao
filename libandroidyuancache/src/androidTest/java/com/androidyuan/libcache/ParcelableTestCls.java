package com.androidyuan.libcache;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * this value need to be mocked by [org.mockito.Mockito],look at {@link "https://gist.github.com/milosmns/7f6448a3602595948449d3bfaff9b005"}.
 */
public class ParcelableTestCls implements Parcelable {


    public static final Creator<ParcelableTestCls> CREATOR = new Creator<ParcelableTestCls>() {


        @Override
        public ParcelableTestCls createFromParcel(Parcel parcel) {
            return new ParcelableTestCls(parcel);
        }

        @Override
        public ParcelableTestCls[] newArray(int size) {
            return new ParcelableTestCls[size];
        }
    };
    public String name = "Jhon";
    public boolean hasBald = true;
    public int age = 35;

    protected ParcelableTestCls() {
    }

    protected ParcelableTestCls(Parcel in) {
        this.name = in.readString();
        this.hasBald = in.readBoolean();
        this.age = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        assert dest != null;
        dest.writeString(this.name);
        dest.writeBoolean(this.hasBald);
        dest.writeInt(this.age);
    }

    //-----------------------below codes is used to make unit test run successfully--------------------------------------------

}
