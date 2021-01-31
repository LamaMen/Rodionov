package com.example.tinkoff.repository;

import com.example.tinkoff.repository.model.Gif;

import java.util.ArrayList;
import java.util.List;

public class FragmentData {
    private final List<Gif> gifList = new ArrayList<>();
    private int numberCurrentGif = -1;
    private int currentPage = 0;

    public void incrementPage() {
        currentPage++;
    }

    public void roilBackPage() {
        currentPage = currentPage > 0 ? currentPage - 1 : 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void incrementNumberGif() {
        numberCurrentGif++;
    }

    public void roilBackNumberGif() {
        numberCurrentGif = numberCurrentGif > 0 ? numberCurrentGif - 1 : 0;
    }

    public Gif getGif() {
        return gifList.get(numberCurrentGif);
    }

    public int getNumberCurrentGif() {
        return numberCurrentGif;
    }

    public int getSize() {
        return gifList.size();
    }

    public void addGif(Gif gif) {
        gifList.add(gif);
    }

    public boolean isFirstGif() {
        return numberCurrentGif == 0;
    }
}
