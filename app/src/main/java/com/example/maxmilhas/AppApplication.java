package com.example.maxmilhas;

import android.app.Application;

import com.example.maxmilhas.Utils.PreferenceUtils;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize sharedPreferences
        PreferenceUtils.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}