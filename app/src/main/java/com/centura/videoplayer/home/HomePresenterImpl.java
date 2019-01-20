package com.centura.videoplayer.home;

import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
public class HomePresenterImpl implements HomeActivityContract.Presenter, HomeActivityContract.onGetDataListener, HomeActivityContract.onItemSelectedClickListener {
    private HomeActivityContract.View mGetDataView;
    private HomeActivityContract.Model mModel;

    HomePresenterImpl(HomeActivityContract.View mGetDataView) {
        this.mGetDataView = mGetDataView;
        mModel = new HomeModelImpl(this);

    }

    @Override
    public void onSuccess(String message, ArrayList<VideoResponseModel> list) {

        mGetDataView.onGetDataSuccess(message, list);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }

    @Override
    public void getVideoListFromApi() {
        mModel.initRetrofitCall();
    }

    @Override
    public void init() {

    }

    @Override
    public void itemClicked(VideoResponseModel videoResponseModel) {
        mGetDataView.startPlayer(videoResponseModel);
    }
}
