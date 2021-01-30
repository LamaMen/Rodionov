package com.example.tinkoff.repository;

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

public class RepositoryImpl implements Repository{
    private final MutableLiveData<Gif> liveData = new MutableLiveData<>();
    private final List<Gif> gifList = new ArrayList<>();
    private int currentGif = -1;
    private int currentPage = 0;

    private static Repository instance;

    private RepositoryImpl() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }

    public void updateGifs() {
        App.getInstance().service.getGifs("latest", currentPage).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();
                    for (GifJson json : returnedPage.results) {
                        gifList.add(new Gif(json));
                    }

                    nextGif();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<Gif> getGif() {
        return liveData;
    }

    @Override
    public void nextGif() {
        if (currentGif < gifList.size() - 1) {
            liveData.setValue(gifList.get(++currentGif));
        } else {
            currentPage++;
            updateGifs();
        }
    }

    @Override
    public void previousGif() {
        if (currentGif > 0) {
            currentGif--;
            liveData.setValue(gifList.get(currentGif));
        }
    }

    @Override
    public boolean isFirst() {
        return currentGif == 0;
    }
}
