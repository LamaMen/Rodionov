package com.example.tinkoff.repository;

import androidx.lifecycle.LiveData;

import com.example.tinkoff.repository.model.Gif;

public interface Repository {

    void loadNewGifs();

    LiveData<Gif> getLiveData();

    void updateGif();

    void nextGif();

    void previousGif();

    boolean isFirst();

    void connect(RepositoryImpl.Observer observer, String chanel);

}
