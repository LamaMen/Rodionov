package com.example.tinkoff.repository.model;

public class Gif {
    private final String name;
    private final String url;
    private final boolean isLast;

    public Gif(GifJson json) {
        name = json.name;
        url = json.url;
        isLast = false;
    }

    public Gif() {
        name = "";
        url = "";
        isLast = true;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isLast() {
        return isLast;
    }
}
