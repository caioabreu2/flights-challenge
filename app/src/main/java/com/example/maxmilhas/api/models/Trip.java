package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trip implements Serializable {

    @SerializedName("layover")
    public Integer layover;

    @SerializedName("stops")
    public Integer stops;

    @SerializedName("aircraft")
    public String aircraft;

    @SerializedName("duration")
    public Integer duration;

    @SerializedName("carrier")
    public String carrier;

    @SerializedName("flightNumber")
    public String flightNumber;

    @SerializedName("from")
    public String from;

    @SerializedName("isInternational")
    public Boolean isInternational;

    @SerializedName("departureDate")
    public String departureDate;

    @SerializedName("arrivalDate")
    public String arrivalDate;

    @SerializedName("to")
    public String to;
}
