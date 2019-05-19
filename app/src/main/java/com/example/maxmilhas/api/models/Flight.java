package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Flight implements Serializable {

    @SerializedName("outbound")
    public List<Outbound> outbound;

    @SerializedName("inbound")
    public List<Inbound> inbound;
}
