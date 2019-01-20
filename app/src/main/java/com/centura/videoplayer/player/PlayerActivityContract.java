package com.centura.videoplayer.player;

import com.centura.videoplayer.BasePresenter;
import com.centura.videoplayer.BaseView;
import com.centura.videoplayer.data.Models.VideoResponseModel;

/**
 * Created by Manikandan Baskaran on 21-01-2019.
 */
public interface PlayerActivityContract {

    interface PlayerView extends BaseView {

        void setUI();

        void expandDesc();

        void collapseDesc();

        void setVideoUrl(String strUrl);
    }

    interface PlayerModel {

    }

    interface PlayerPresenter extends BasePresenter {

        void itemClicked(VideoResponseModel videoResponseModel);

        void onExpandOrCollapse(boolean isCollapsed);

    }

}
