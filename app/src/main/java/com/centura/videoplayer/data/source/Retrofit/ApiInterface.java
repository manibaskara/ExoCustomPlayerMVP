package com.centura.videoplayer.data.source.Retrofit;

import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
public interface ApiInterface {

    @GET("media.json?print=pretty")
    Call<ArrayList<VideoResponseModel>> callVideoListApi();
}
