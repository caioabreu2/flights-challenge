package com.example.maxmilhas.main;

import com.example.maxmilhas.api.models.Flight;

public interface MainContract {

    interface View {

        void respond(Flight flight);
    }

    interface Presenter {

        void getFlights();

    }


}
