package com.centura.videoplayer.home;

import com.centura.videoplayer.BasePresenter;
import com.centura.videoplayer.BaseView;
import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
interface HomeActivityContract {
    interface View extends BaseView {
        void onGetDataSuccess(String message, ArrayList<VideoResponseModel> list);

        void onGetDataFailure(String message);

        void startPlayer(VideoResponseModel videoResponseModel);
    }

    interface Presenter extends BasePresenter {
        void getVideoListFromApi();
    }

    interface Model {
        void initRetrofitCall();
    }

    interface onGetDataListener {

        void onSuccess(String message, ArrayList<VideoResponseModel> list);

        void onFailure(String message);
    }

    interface onItemSelectedClickListener {
        void itemClicked(VideoResponseModel videoResponseModel);
    }
}