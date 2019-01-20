package com.centura.videoplayer.home;

import com.centura.videoplayer.data.source.Retrofit.ApiInterface;
import com.centura.videoplayer.data.source.Retrofit.ApiServiceGenerator;
import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
public class HomeModelImpl implements HomeActivityContract.Model {

    private HomeActivityContract.onGetDataListener onGetDataListener;

    HomeModelImpl(HomeActivityContract.onGetDataListener onGetDataListener) {
        this.onGetDataListener = onGetDataListener;
    }

    @Override
    public void initRetrofitCall() {
        ApiInterface apiInterface = ApiServiceGenerator.createService().create(ApiInterface.class);
        Call<ArrayList<VideoResponseModel>> call = apiInterface.callVideoListApi();
        call.enqueue(new Callback<ArrayList<VideoResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VideoResponseModel>> call, Response<ArrayList<VideoResponseModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    ArrayList<VideoResponseModel> videoResponseModels = response.body();
                    onGetDataListener.onSuccess("success", videoResponseModels);
                } else {
                    onGetDataListener.onFailure("Failed with " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VideoResponseModel>> call, Throwable t) {
                onGetDataListener.onFailure("Something went wrong. please try again");
            }
        });
    }
}
