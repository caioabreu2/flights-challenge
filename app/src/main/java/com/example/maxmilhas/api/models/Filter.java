package com.example.maxmilhas.api.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable {

    private ArrayList<String> selectedFilters;

    public Filter(ArrayList<String> selectedFilters) {
        this.selectedFilters = selectedFilters;
    }

    public ArrayList<String> getSelectedFilters() {
        return selectedFilters;
    }

    public void setSelectedFilters(ArrayList<String> selectedFilters) {
        this.selectedFilters = selectedFilters;
    }
}
