package com.example.tinkoff;

import android.app.Application;

import com.example.tinkoff.repository.GifService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    // Базовый url для работы с API
    private static final String BASE_URL = "https://developerslife.ru/";

    private static App instance;

    public GifService service;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
    }

    // Инициализация Retrofit 2
    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GifService.class);
    }
}
