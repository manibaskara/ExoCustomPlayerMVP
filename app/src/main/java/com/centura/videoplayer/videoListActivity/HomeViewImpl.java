package com.centura.videoplayer.videoListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.centura.videoplayer.base.BaseViewImpl;
import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.playerActivity.PlayerViewImpl;
import com.centura.videoplayer.videoListActivity.adapter.VideoHomeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HomeViewImpl extends BaseViewImpl implements HomeActContract.View {

    private RecyclerView rvVideoList;
    private HomePresenterImpl homePresenter;
    private ArrayList<VideoResponseModel> videoResponseModelArrayList;

    public static final String TRACK_LIST = "track_list";
    public static final String VIDEO_LIST_MASTER = "video_list_master";
    public static final String SELECTED_VIDEO_ID = "selected_video";

    private static final String TAG = "log_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = new HomePresenterImpl(this);
        homePresenter.init();
        homePresenter.getVideoListFromApi();
    }

    @Override
    public void init() {
        TextView tvUserName = findViewById(R.id.tvUserName);
        rvVideoList = findViewById(R.id.rvVideoList);
        rvVideoList.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("user_name") != null) {
            tvUserName.setText(String.format("Logged in as %s!", intent.getStringExtra("user_name")));
        }
    }

    @Override
    public void onGetDataSuccess(String message, ArrayList<VideoResponseModel> list) {
        Log.d(TAG, "onGetDataSuccess: " + message);
        videoResponseModelArrayList = list;
        VideoHomeAdapter videoHomeAdapter = new VideoHomeAdapter(this, list, homePresenter);
        rvVideoList.setAdapter(videoHomeAdapter);
    }

    @Override
    public void onGetDataFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startPlayer(int position, VideoResponseModel videoResponseModel) {

        Collections.swap(videoResponseModelArrayList, 0, position);

        HashMap<String, Integer> trackAndPositionMap = new HashMap<>();

        for (int trackPosition = 0; trackPosition < videoResponseModelArrayList.size(); trackPosition++) {
            trackAndPositionMap.put(videoResponseModelArrayList.get(trackPosition).getId(), trackPosition);
        }
        Intent intent = new Intent(HomeViewImpl.this, PlayerViewImpl.class);

        intent.putExtra(SELECTED_VIDEO_ID, videoResponseModel.getId());
        intent.putExtra(TRACK_LIST, trackAndPositionMap);
        intent.putExtra(VIDEO_LIST_MASTER, videoResponseModelArrayList);
        startActivity(intent);
    }
}