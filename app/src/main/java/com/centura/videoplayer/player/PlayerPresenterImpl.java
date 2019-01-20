package com.centura.videoplayer.player;

import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.google.android.exoplayer2.ui.PlayerControlView;

/**
 * Created by Manikandan Baskaran on 21-01-2019.
 */
public class PlayerPresenterImpl implements PlayerActivityContract.PlayerPresenter {

    private PlayerActivityContract.PlayerView mPlayerView;

    PlayerPresenterImpl(PlayerActivityContract.PlayerView playerView) {
        this.mPlayerView = playerView;
        mPlayerView.setUI();
    }

    @Override
    public void init() {

    }

    @Override
    public void itemClicked(VideoResponseModel videoResponseModel) {
        mPlayerView.setVideoUrl(videoResponseModel.getUrl());
    }

    @Override
    public void onExpandOrCollapse(boolean isCollapsed) {
        if (isCollapsed)
            mPlayerView.expandDesc();
        else mPlayerView.collapseDesc();
    }
}
