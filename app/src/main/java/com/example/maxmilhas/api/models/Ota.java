package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ota implements Serializable {

    @SerializedName("adult")
    public Adult adult;

    @SerializedName("luggage")
    public Luggage luggage;

    @SerializedName("checkout")
    public Checkout checkout;

    @SerializedName("familyCode")
    public String familyCode;

    @SerializedName("fareTotal")
    public Double fareTotal;

    @SerializedName("saleTotal")
    public Double saleTotal;
}
