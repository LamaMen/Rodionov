package com.example.tinkoff.repository.model;

public class Gif {
    private final int id;
    private final String name;
    private final String url;


    public Gif(GifJson json) {
        id = json.id;
        name = json.name;
        url = json.url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
