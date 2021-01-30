package com.example.tinkoff.repository;

import androidx.lifecycle.LiveData;

import com.example.tinkoff.repository.model.Gif;

public interface Repository {

    void updateGifs();

    LiveData<Gif> getGif();

    void updateGif();

    void nextGif();

    void previousGif();

    boolean isFirst();

    void connect(RepositoryImpl.Observer observer);

    void disconnect();
}
