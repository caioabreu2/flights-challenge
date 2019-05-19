package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fee implements Serializable {

    @SerializedName("type")
    public String type;

    @SerializedName("value")
    public Double value;

    @SerializedName("group")
    public String group;

    @SerializedName("passengerType")
    public String passengerType;

    @SerializedName("id")
    public Integer id;
}
