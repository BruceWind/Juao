package com.example.cacheexample.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SerlzEngineer implements Serializable {
    private String name = "Jhon";
    private boolean hasBald = true;
    private int age = 35;
    private String characteristic = "996";

    public String getName() {
        return name;
    }

    public boolean hasBald() {
        return hasBald;
    }

    @NonNull
    @Override
    public String toString() {
        return "{name : " + name + " , hasBald : " + hasBald + " ,characteristic : " + characteristic + "}";
    }
}
