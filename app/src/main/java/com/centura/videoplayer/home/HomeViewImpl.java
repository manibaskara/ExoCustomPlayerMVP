package com.centura.videoplayer.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.home.adapter.HomeVideoAdapter;
import com.centura.videoplayer.player.PlayerActivityViewImpl;

import java.util.ArrayList;

public class HomeViewImpl extends AppCompatActivity implements HomeActivityContract.View {

    private TextView tvUserName;
    private RecyclerView rvVideoList;
    private HomePresenterImpl homePresenter;
    private ProgressBar pgStatus;
    private ArrayList<VideoResponseModel> videoResponseModelArrayList;
    public static final String VIDEO_LIST = "video_list";
    public static final String SELECTED_VIDEO = "selected_video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = new HomePresenterImpl(this);
        init();
        onClicks();

    }

    @Override
    public void init() {
        tvUserName = findViewById(R.id.tvUserName);
        rvVideoList = findViewById(R.id.rvVideoList);
        pgStatus = findViewById(R.id.pgStatus);
        rvVideoList.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("user_name") != null) {
            String strUser = intent.getStringExtra("user_name");
            tvUserName.setText(String.format("Logged in as %s!", strUser));
        }

        homePresenter.getVideoListFromApi();
    }

    @Override
    public void onClicks() {
    }

    @Override
    public void showProgress() {
        pgStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgStatus.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataSuccess(String message, ArrayList<VideoResponseModel> list) {
        // rowPresenter = new VideoAdapterPresenterImpl(list);
        videoResponseModelArrayList = list;
        HomeVideoAdapter homeVideoAdapter = new HomeVideoAdapter(this, list, homePresenter);
        rvVideoList.setAdapter(homeVideoAdapter);
    }

    @Override
    public void onGetDataFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startPlayer(VideoResponseModel videoResponseModel) {
        Intent intent = new Intent(HomeViewImpl.this, PlayerActivityViewImpl.class);
        intent.putExtra(SELECTED_VIDEO, videoResponseModel);
        intent.putExtra(VIDEO_LIST, videoResponseModelArrayList);
        startActivity(intent);
    }
}