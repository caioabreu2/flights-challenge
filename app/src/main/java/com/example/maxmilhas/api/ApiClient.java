package com.example.maxmilhas.api;

import com.example.maxmilhas.BuildConfig;
import com.example.maxmilhas.api.models.Flight;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final long HTTP_TIMEOUT = 30000;
    private static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.BASE_URL;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client;

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder()
                    .readTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    private static ApiServices getApiServices() {
        return ApiClient.getRetrofitInstance().create(ApiServices.class);
    }

    public static void getFlights(Callback<Flight> callback) {
        Call<Flight> call = getApiServices().getFlights();
        call.enqueue(callback);
    }
}
