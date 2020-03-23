package com.example.cacheexample.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ParceEngineer implements Parcelable {
    public static final Parcelable.Creator<ParceEngineer> CREATOR = new Parcelable.Creator<ParceEngineer>() {
        @Override
        public ParceEngineer createFromParcel(Parcel source) {
            return new ParceEngineer(source);
        }

        @Override
        public ParceEngineer[] newArray(int size) {
            return new ParceEngineer[size];
        }
    };
    private String name = "Jhon";
    private boolean hasBald = true;
    private int age = 35;
    private String characteristic = "996";

    public ParceEngineer() {
    }

    protected ParceEngineer(Parcel in) {
        this.name = in.readString();
        this.hasBald = in.readByte() != 0;
        this.age = in.readInt();
        this.characteristic = in.readString();
    }

    public String getName() {
        return name;
    }

    public boolean hasBald() {
        return hasBald;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.hasBald ? (byte) 1 : (byte) 0);
        dest.writeInt(this.age);
        dest.writeString(this.characteristic);
    }

    @NonNull
    @Override
    public String toString() {
        return "{name : " + name + " , hasBald : " + hasBald + " ,characteristic : " + characteristic + "}";
    }
}
