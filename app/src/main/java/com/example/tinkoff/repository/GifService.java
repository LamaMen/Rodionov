package com.example.tinkoff.repository;

import com.example.tinkoff.repository.model.GifList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GifService {
    @GET("{type}/{page}?json=true")
    Call<GifList> getGifs(@Path("type") String type, @Path("page") int page);
}
