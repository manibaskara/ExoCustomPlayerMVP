package com.centura.videoplayer.playerActivity;

import com.centura.videoplayer.data.source.Retrofit.room.VideoInfo;
import com.centura.videoplayer.data.source.Retrofit.room.VideoInfoDAO;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
public class PlayerActivityModelImpl implements PlayerActivityContract.Model {

    private VideoInfoDAO videoInfoDAO;

    PlayerActivityModelImpl(VideoInfoDAO videoInfoDAO) {
        this.videoInfoDAO = videoInfoDAO;
    }

    @Override
    public void addVideoInfo(VideoInfo videoInfo) {
       long i =  videoInfoDAO.insertVideoInfo(videoInfo);

    }

    @Override
    public VideoInfo getVideoInfo(String videoId) {
        return videoInfoDAO.getVideoInfo(videoId);
    }
}