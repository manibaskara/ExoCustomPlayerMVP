package com.centura.videoplayer.videoListActivity;

import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
public class HomePresenterImpl implements HomeActContract.Presenter, HomeActContract.onGetDataListener, HomeActContract.onItemSelectedClickListener {
    private HomeActContract.View mHomeView;
    private HomeActContract.Model mModel;

    HomePresenterImpl(HomeActContract.View mHomeView) {
        this.mHomeView = mHomeView;
        mModel = new HomeModelImpl(this);


    }

    @Override
    public void onSuccess(String message, ArrayList<VideoResponseModel> list) {
        mHomeView.showDialog("", false);
        mHomeView.onGetDataSuccess(message, list);
    }

    @Override
    public void onFailure(String message) {

        mHomeView.showDialog("", false);
        mHomeView.onGetDataFailure(message);
    }

    @Override
    public void getVideoListFromApi() {
        mHomeView.showDialog("Getting Video List", true);
        mModel.initRetrofitCall();
    }

    @Override
    public void init() {
        mHomeView.init();
        mHomeView.onClicks();
    }

    @Override
    public void itemClicked(int position, VideoResponseModel videoResponseModel) {

        mHomeView.startPlayer(position, videoResponseModel);
    }
}
