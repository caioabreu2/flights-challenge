package com.example.maxmilhas.api;

import com.example.maxmilhas.api.models.Flight;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServices {

    @GET("hmg-search")
    Call<Flight> getFlights();
}
