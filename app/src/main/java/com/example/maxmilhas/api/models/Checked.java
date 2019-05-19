package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Checked implements Serializable {

    @SerializedName("weight")
    public Object weight;

    @SerializedName("prices")
    public List<Object> prices = null;
}
