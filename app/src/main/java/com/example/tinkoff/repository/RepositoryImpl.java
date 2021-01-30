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

public class RepositoryImpl implements Repository {
    private static Repository instance;
    private final MutableLiveData<Gif> liveData = new MutableLiveData<>();
    private final List<Gif> gifList = new ArrayList<>();
    private Observer observer;
    private int currentGif = -1;
    private int currentPage = 0;

    private RepositoryImpl() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }

    public void updateGifs() {
        observer.loading();

        App.getInstance().service.getGifs("latest", currentPage).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();
                    for (GifJson json : returnedPage.results) {
                        gifList.add(new Gif(json));
                    }

                    observer.done();
                    nextGif();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                observer.done();
                observer.error();
                currentPage--;
            }
        });
    }

    public LiveData<Gif> getGif() {
        return liveData;
    }

    @Override
    public void updateGif() {
        if (currentGif < gifList.size() && currentGif != -1) {
            liveData.setValue(gifList.get(currentGif));
        } else {
            currentPage++;
            updateGifs();
        }
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

    @Override
    public void connect(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void disconnect() {
        this.observer = null;
    }

    public interface Observer {
        void error();

        void loading();

        void done();
    }
}
