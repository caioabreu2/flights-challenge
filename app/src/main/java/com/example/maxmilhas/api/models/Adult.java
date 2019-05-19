package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Adult implements Serializable {

    @SerializedName("quantity")
    public Integer quantity;

    @SerializedName("fare")
    public Double fare;

    @SerializedName("fees")
    public List<Fee> fees = null;

}
