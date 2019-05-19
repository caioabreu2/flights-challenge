package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Checkout implements Serializable {

    @SerializedName("type")
    public String type;

    @SerializedName("target")
    public String target;
}
