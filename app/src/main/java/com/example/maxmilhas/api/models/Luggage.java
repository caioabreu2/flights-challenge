package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Luggage implements Serializable {

    @SerializedName("carryOn")
    public CarryOn carryOn;

    @SerializedName("checked")
    public Checked checked;
}
