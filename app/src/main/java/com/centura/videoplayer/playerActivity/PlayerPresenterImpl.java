package com.centura.videoplayer.playerActivity;

import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.data.source.room.VideoInfo;
import com.centura.videoplayer.data.source.room.VideoInfoDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
public class PlayerPresenterImpl implements PlayerActContract.Presenter {

    private PlayerActContract.View playerActView;
    private ArrayList<VideoResponseModel> videoResponseModelArrayList;
    private HashMap<String, Integer> trackAndPositionMap;
    private PlayerModelImpl videoInfoModel;

    PlayerPresenterImpl(PlayerActContract.View playerActView
            , ArrayList<VideoResponseModel> videoResponseModelArrayList
            , HashMap<String, Integer> trackAndPositionMap
            , VideoInfoDAO videoInfoDAO) {
        this.playerActView = playerActView;
        this.videoResponseModelArrayList = videoResponseModelArrayList;
        this.trackAndPositionMap = trackAndPositionMap;
        videoInfoModel = new PlayerModelImpl(videoInfoDAO);
    }

    @Override
    public void init() {
        playerActView.initPlayerAndSetUI();
        playerActView.onClicks();

    }

    @Override
    public void expandOnclick(int MAX_LINES) {

        if (MAX_LINES == 3)
            playerActView.expandInfo();
        else playerActView.collapseInfo();

    }

    @Override
    public void onItemSelected(String videoId) {
        VideoResponseModel videoResponseModelSelected = null;
        ArrayList<VideoResponseModel> tempArrayList = new ArrayList<>();
        for (int i = 0; i < videoResponseModelArrayList.size(); i++) {
            VideoResponseModel videoResponseModel = videoResponseModelArrayList.get(i);
            if (videoResponseModel.getId().equals(videoId)) {
                videoResponseModelSelected = videoResponseModel;
            } else {
                tempArrayList.add(videoResponseModel);
            }
        }

        VideoInfo videoInfo = videoInfoModel.getVideoInfo(videoId);

        if (videoInfo != null)
            playerActView.changeTrackOnSelect(trackAndPositionMap.get(videoId)
                    , videoResponseModelSelected
                    , tempArrayList
                    , videoInfo.getIsEnded()
                    , videoInfo.getLastPositionMillis());
        else playerActView.changeTrackOnSelect(trackAndPositionMap.get(videoId)
                , videoResponseModelSelected
                , tempArrayList
                , false
                , -1);
    }

    @Override
    public void onTrackEnded(int trackPosition) {
        setUI(getKeyByValue(trackAndPositionMap, trackPosition));
    }

    @Override
    public void setUI(String firstVideoId) {
        VideoResponseModel videoResponseModelSelected = null;
        ArrayList<VideoResponseModel> tempArrayList = new ArrayList<>();
        for (int i = 0; i < videoResponseModelArrayList.size(); i++) {
            VideoResponseModel videoResponseModel = videoResponseModelArrayList.get(i);
            if (videoResponseModel.getId().equals(firstVideoId)) {
                videoResponseModelSelected = videoResponseModel;
            } else {
                tempArrayList.add(videoResponseModel);
            }
        }

        playerActView.setUI(videoResponseModelSelected, tempArrayList);
    }

    @Override
    public VideoInfo getVideoInfo(String videoId) {
        return videoInfoModel.getVideoInfo(videoId);
    }

    @Override
    public void insertVideoInfo(int position, long currentMillis, boolean isEnded) {
        String videoId = getKeyByValue(trackAndPositionMap, position);
        if (videoId!=null)
        videoInfoModel.addVideoInfo(new VideoInfo(videoId, currentMillis, isEnded));
    }

    @Override
    public void onClearHistoryButton() {
        videoInfoModel.deleteAllInfo();
        playerActView.showToast("Cleared Player History.");
    }


    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
