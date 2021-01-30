package com.example.tinkoff.repository;

import androidx.lifecycle.LiveData;

import com.example.tinkoff.repository.model.Gif;

public interface Repository {

    void updateGifs();

    LiveData<Gif> getGif();

    void nextGif();

    void previousGif();

    boolean isFirst();

}
