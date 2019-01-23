package com.centura.videoplayer.videoListActivity;

import com.centura.videoplayer.base.BasePresenter;
import com.centura.videoplayer.base.BaseView;
import com.centura.videoplayer.data.Models.VideoResponseModel;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 20-01-2019.
 */
interface HomeActContract {
    interface View extends BaseView {
        void onGetDataSuccess(String message, ArrayList<VideoResponseModel> list);

        void onGetDataFailure(String message);

        void startPlayer(int position, VideoResponseModel videoResponseModel);
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
        void itemClicked(int position, VideoResponseModel videoResponseModel);
    }
}