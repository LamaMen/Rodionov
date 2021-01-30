package com.example.tinkoff.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tinkoff.App;
import com.example.tinkoff.repository.model.Gif;
import com.example.tinkoff.repository.model.GifJson;
import com.example.tinkoff.repository.model.GifList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final MutableLiveData<Gif> liveData = new MutableLiveData<>();
    private List<Gif> gifList = new ArrayList<>();
    private int currentGif = 0;

    public void getGifs() {
        App.getInstance().service.getGifs("latest", 0).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();
                    for (GifJson json : returnedPage.results) {
                        gifList.add(new Gif(json));
                    }

                    liveData.setValue(gifList.get(2));
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<Gif> getLiveData() {
        return liveData;
    }

}
