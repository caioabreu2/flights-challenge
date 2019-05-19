package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CarryOn implements Serializable {

    @SerializedName("weight")
    public Integer weight;

    @SerializedName("prices")
    public List<Integer> prices = null;
}
