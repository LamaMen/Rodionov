package com.example.tinkoff.repository.model;

import com.google.gson.annotations.SerializedName;

public class GifJson {
    @SerializedName("description")
    public String name;
    @SerializedName("gifURL")
    public String url;
}
