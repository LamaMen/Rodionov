package com.example.tinkoff.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GifList {
    @SerializedName("result")
    public List<GifJson> results;
}
