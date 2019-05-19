package com.example.maxmilhas.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

    public static String getFormattedTime(String dateTime) {
        return dateTime.split("T")[1].substring(0,5);
    }

    public static String getFormattedHours(int durationMinutes) {
        return durationMinutes / 60 + "h:" + durationMinutes % 60;
    }
}
