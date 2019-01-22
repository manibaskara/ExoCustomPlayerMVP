package com.centura.videoplayer.playerActivity;

import com.centura.videoplayer.base.BasePresenter;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.data.source.Retrofit.room.VideoInfo;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
public interface PlayerActivityContract {

    interface View {

        void init();

        void initPlayerAndSetUI();

        void setUI(VideoResponseModel playingVideoModel, ArrayList<VideoResponseModel> remainingArrModels);

        void onClicks();

        void expandInfo();

        void collapseInfo();

        void changeTrackOnSelect(int position
                , VideoResponseModel videoResponseModel
                , ArrayList<VideoResponseModel> remainingArrModels
                , boolean isEnded
                , long seekTo);

    }

    interface Presenter extends BasePresenter {

        void init();

        void expandOnclick(int MAX_LINES);

        void onItemSelected(String videoId);

        void onTrackEnded(int trackPosition);

        void setUI(String firstVideoId);

        VideoInfo getVideoInfo(String videoId);

        void insertVideoInfo(int position, long currentMillis, boolean isEnded);
    }

    interface Model {

        void addVideoInfo(VideoInfo videoInfo);

        VideoInfo getVideoInfo(String videoId);

    }

}
