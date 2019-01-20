package com.centura.videoplayer.player;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.player.adapter.RelatedVideoAdapter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.centura.videoplayer.home.HomeViewImpl.SELECTED_VIDEO;
import static com.centura.videoplayer.home.HomeViewImpl.VIDEO_LIST;

public class PlayerActivityViewImpl extends AppCompatActivity implements PlayerActivityContract.PlayerView {

    private VideoResponseModel videoResponseModel;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ArrayList<VideoResponseModel> videoResponseModelArrayList;
    private RecyclerView rvRelated;
    private TextView tvTitle, tvDesc;

    private PlayerPresenterImpl playerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();


    }

    @Override
    public void init() {
        playerView = findViewById(R.id.exo_player);
        rvRelated = findViewById(R.id.rvRelated);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);


        Intent intent = getIntent();
        if (intent != null && intent.getSerializableExtra(VIDEO_LIST) != null && intent.getSerializableExtra(SELECTED_VIDEO) != null) {
            videoResponseModel = (VideoResponseModel) intent.getSerializableExtra(SELECTED_VIDEO);
            videoResponseModelArrayList = (ArrayList<VideoResponseModel>) intent.getSerializableExtra(VIDEO_LIST);
        }

        playerPresenter = new PlayerPresenterImpl(this);

        initPlayer();
    }

    private void initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setPlayer(player);

        DefaultDataSourceFactory defaultDataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "video player"));

        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(Uri.parse(videoResponseModel.getUrl()));

        player.prepare(extractorMediaSource);
        player.setPlayWhenReady(true);
        playerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClicks() {

        tvTitle.setOnClickListener(view -> {
            if (tvDesc.getMaxLines() == 2)
                playerPresenter.onExpandOrCollapse(true);
            else
                playerPresenter.onExpandOrCollapse(false);
        });

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.getCurrentPosition();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    @Override
    public void setUI() {
        tvTitle.setText(videoResponseModel.getTitle());
        tvDesc.setText(videoResponseModel.getDescription());
        rvRelated.setLayoutManager(new LinearLayoutManager(this));
        rvRelated.setAdapter(new RelatedVideoAdapter(this, videoResponseModelArrayList, playerPresenter));
    }

    @Override
    public void expandDesc() {
        tvDesc.setMaxLines(Integer.MAX_VALUE);
    }

    @Override
    public void collapseDesc() {
        tvDesc.setMaxLines(2);

    }

    @Override
    public void setVideoUrl(String strUrl) {
        DefaultDataSourceFactory defaultDataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "video player"));

        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(Uri.parse(strUrl));

        player.stop();
        player.prepare(extractorMediaSource);
        player.setPlayWhenReady(true);
    }
}