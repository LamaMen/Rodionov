package com.example.tinkoff.repository.model;

import com.google.gson.annotations.SerializedName;

public class GifJson {
    @SerializedName("id")
    public int id;
    @SerializedName("description")
    public String name;
    @SerializedName("gifURL")
    public String url;
}
