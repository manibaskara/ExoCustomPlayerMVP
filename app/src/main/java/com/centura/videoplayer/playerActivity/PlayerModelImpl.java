package com.centura.videoplayer.playerActivity;

import com.centura.videoplayer.data.source.room.VideoInfo;
import com.centura.videoplayer.data.source.room.VideoInfoDAO;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
public class PlayerModelImpl implements PlayerActContract.Model {

    private VideoInfoDAO videoInfoDAO;

    PlayerModelImpl(VideoInfoDAO videoInfoDAO) {
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

    @Override
    public void deleteAllInfo() {
        videoInfoDAO.deleteAll();
    }
}