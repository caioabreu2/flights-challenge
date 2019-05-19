package com.example.maxmilhas.main;

import com.example.maxmilhas.api.ApiClient;
import com.example.maxmilhas.api.models.Flight;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainContractView;

    MainPresenter(MainContract.View mainContractView) {
        this.mainContractView = mainContractView;
    }

    @Override
    public void getFlights() {
        ApiClient.getFlights(new Callback<Flight>() {
            @Override
            public void onResponse(Call<Flight> call, Response<Flight> response) {
                if (response.isSuccessful()) {
                    Flight flight = response.body();
                    mainContractView.respond(flight);
                } else {
                    //TODO: Show some error to user
                }
            }

            @Override
            public void onFailure(Call<Flight> call, Throwable t) {
                //TODO: Show some error to user
            }
        });
    }
}
