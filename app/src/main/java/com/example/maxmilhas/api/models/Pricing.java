package com.example.maxmilhas.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pricing implements Serializable {

    @SerializedName("ota")
    public Ota ota;

    @SerializedName("airlineName")
    public String airlineName;

    @SerializedName("isInternational")
    public Boolean isInternational;

    @SerializedName("bestPriceAt")
    public String bestPriceAt;

    @SerializedName("mmBestPriceAt")
    public String mmBestPriceAt;

    @SerializedName("savingPercentage")
    public Double savingPercentage;

    public Double getSaleTotal() {
        double price = 0.0;
        if (ota != null) {
            price = ota.saleTotal;
        }
        return price;
    }

    public String getFormattedTotalPrice() {
        String totalPrice = "------";
        if (ota != null) {
            totalPrice = "R$ " + ota.saleTotal;
        }
        return totalPrice;
    }
}
