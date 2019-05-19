package com.example.maxmilhas.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferenceUtils {

    public static final String ORDER = "ORDER";
    public static final String FILTERS = "FILTERS";

    private static final String PREFERENCES_NAME = "APP_PREFERENCES";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void set(String key, Object value) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        sharedPreferences.edit().putString(key, json).apply();
    }

    public static <T> T get(String key, Class<T> clazz) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        T value = (json == null) ? null : gson.fromJson(json, clazz);
        return value;
    }

    public static void removeByKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public static void removeAll() {
        sharedPreferences.edit().clear().apply();
    }
}