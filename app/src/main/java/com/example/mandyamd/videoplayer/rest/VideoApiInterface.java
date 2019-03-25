package com.example.mandyamd.videoplayer.rest;

import com.example.mandyamd.videoplayer.model.VideoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoApiInterface {
    @GET("videos.json")
    Call<List<VideoData>> getVideoData();
}