package com.example.tinkoff.repository;

import android.util.Log;

import com.example.tinkoff.App;
import com.example.tinkoff.repository.model.GifJson;
import com.example.tinkoff.repository.model.GifList;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public void getGifs() {
        App.getInstance().service.getGifs("latest", 0).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();
                    for (GifJson json : returnedPage.results) {
                        Log.d("Repository", json.name);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
