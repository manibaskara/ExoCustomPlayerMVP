package com.centura.videoplayer.data.source.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
public class ApiServiceGenerator {

    private static final String BASE_URL = "https://interview-e18de.firebaseio.com/";

    public static Retrofit createService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(increasedTimeOutClient())
                .build();
    }

    private static OkHttpClient increasedTimeOutClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
    }
}
