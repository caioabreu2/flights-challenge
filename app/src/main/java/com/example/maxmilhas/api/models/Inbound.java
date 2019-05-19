package com.example.maxmilhas.api.models;

import com.example.maxmilhas.Utils.TimeUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Inbound implements Serializable {

    @SerializedName("stops")
    public Integer stops;

    @SerializedName("airline")
    public String airline;

    @SerializedName("otaAvailableIn")
    public String otaAvailableIn;

    @SerializedName("duration")
    public Integer duration;

    @SerializedName("flightNumber")
    public String flightNumber;

    @SerializedName("airlineTarget")
    public String airlineTarget;

    @SerializedName("from")
    public String from;

    @SerializedName("id")
    public String id;

    @SerializedName("choosedTripType")
    public String choosedTripType;

    @SerializedName("availableIn")
    public String availableIn;

    @SerializedName("trips")
    public List<Trip> trips = null;

    @SerializedName("departureDate")
    public String departureDate;

    @SerializedName("arrivalDate")
    public String arrivalDate;

    @SerializedName("cabin")
    public String cabin;

    @SerializedName("pricing")
    public Pricing pricing;

    @SerializedName("direction")
    public String direction;

    @SerializedName("to")
    public String to;

    public String getStopsFormatted() {
        if (stops == 0) {
            return "voo direto";
        } else if (stops == 1) {
            return 1 + " parada";
        } else {
            return stops + " paradas";
        }
    }

    public String getFormattedDurationAndStops() {
        return TimeUtils.getFormattedHours(duration) + ", " + getStopsFormatted();
    }

    private boolean enableInbound = true;

    public void setInboundEnable(boolean enable) {
        this.enableInbound = enable;
    }

    public boolean isInboundEnable() {
        return enableInbound;
    }

    public boolean isMorningTime() {
        String flightTime = TimeUtils.getFormattedTime(departureDate);
        String startTime = "06:00";
        String endTime = "11:59";
        int compareStart = flightTime.compareTo(startTime);
        int compareEnd;

        if (compareStart >= 0) {
            compareEnd = flightTime.compareTo(endTime);
            if (compareEnd <= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isAfternoonTime() {
        String flightTime = TimeUtils.getFormattedTime(departureDate);
        String startTime = "12:00";
        String endTime = "17:59";
        int compareStart = flightTime.compareTo(startTime);
        int compareEnd;

        if (compareStart >= 0) {
            compareEnd = flightTime.compareTo(endTime);
            if (compareEnd <= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isNightTime() {
        String flightTime = TimeUtils.getFormattedTime(departureDate);
        String startTime = "18:00";
        String endTime = "23:59";
        int compareStart = flightTime.compareTo(startTime);
        int compareEnd;

        if (compareStart >= 0) {
            compareEnd = flightTime.compareTo(endTime);
            if (compareEnd <= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isDawnTime() {
        String flightTime = TimeUtils.getFormattedTime(departureDate);
        String startTime = "00:00";
        String endTime = "05:59";
        int compareStart = flightTime.compareTo(startTime);
        int compareEnd;

        if (compareStart >= 0) {
            compareEnd = flightTime.compareTo(endTime);
            if (compareEnd <= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isNonStopFlight() {
        return stops == 0;
    }

    public boolean isOneStopFlight() {
        return stops == 1;
    }
}
