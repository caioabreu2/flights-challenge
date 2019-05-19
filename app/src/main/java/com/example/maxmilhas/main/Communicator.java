package com.example.maxmilhas.main;

import com.example.maxmilhas.api.models.Flight;

import java.util.ArrayList;

public interface Communicator {

    void respond(Flight flight);
    void sortAndUpdateFlights(String type);
    void filterAndUpdateFlights(ArrayList<String> resultSelectedTimeFilters, ArrayList<String> resultSelectedStopsFilters);

}
