package com.example.tinkoff.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tinkoff.App;
import com.example.tinkoff.repository.model.Gif;
import com.example.tinkoff.repository.model.GifJson;
import com.example.tinkoff.repository.model.GifList;
import com.example.tinkoff.view.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryImpl implements Repository {
    private static Repository instance;
    private final MutableLiveData<Gif> liveData = new MutableLiveData<>();
    Map<String, FragmentData> dataMap = new HashMap<>();
    private Observer observer;
    private String currentChanel;

    private RepositoryImpl() {
        for (int i = 0; i < 2; i++) {
            dataMap.put(MainActivity.chancels[i], new FragmentData());
        }
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }

    public void updateGifs() {
        observer.loading();

        App.getInstance().service.getGifs(currentChanel, dataMap.get(currentChanel).getCurrentPage()).enqueue(new Callback<GifList>() {
            @Override
            public void onResponse(@NotNull Call<GifList> call, @NotNull Response<GifList> response) {
                if (response.isSuccessful()) {
                    GifList returnedPage = response.body();
                    for (GifJson json : returnedPage.results) {
                        dataMap.get(currentChanel).addGif(new Gif(json));
                    }

                    observer.done();
                    nextGif();
                }
            }

            @Override
            public void onFailure(@NotNull Call<GifList> call, @NotNull Throwable t) {
                observer.done();
                observer.error();
                dataMap.get(currentChanel).roilBackPage();
            }
        });
    }

    public LiveData<Gif> getGif() {
        return liveData;
    }

    @Override
    public void updateGif() {
        FragmentData data = dataMap.get(currentChanel);
        if (data.getNumberCurrentGif() < data.getSize() && data.getNumberCurrentGif() != -1) {
            liveData.setValue(data.getGif());
        } else {
            data.incrementPage();
            updateGifs();
        }
    }

    @Override
    public void nextGif() {
        FragmentData data = dataMap.get(currentChanel);
        if (data.getNumberCurrentGif() < data.getSize() - 1) {
            data.incrementNumberGif();
            liveData.setValue(data.getGif());
        } else {
            data.incrementPage();
            updateGifs();
        }
    }

    @Override
    public void previousGif() {
        dataMap.get(currentChanel).roilBackNumberGif();
        liveData.setValue(dataMap.get(currentChanel).getGif());
    }

    @Override
    public boolean isFirst() {
        return dataMap.get(currentChanel).isFirstGif();
    }

    @Override
    public void connect(Observer observer, String chanel) {
        this.observer = observer;
        currentChanel = chanel;
    }


    public interface Observer {
        void error();

        void loading();

        void done();
    }
}
