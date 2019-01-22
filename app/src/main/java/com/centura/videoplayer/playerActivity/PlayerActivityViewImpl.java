package com.centura.videoplayer.playerActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.centura.videoplayer.base.BaseViewImpl;
import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.data.source.Retrofit.room.VideoInfo;
import com.centura.videoplayer.data.source.Retrofit.room.VideoInfoDatabase;
import com.centura.videoplayer.playerActivity.adapter.RecentListAdapter;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

import static com.centura.videoplayer.videoListActivity.HomeViewImpl.SELECTED_VIDEO_ID;
import static com.centura.videoplayer.videoListActivity.HomeViewImpl.TRACK_LIST;
import static com.centura.videoplayer.videoListActivity.HomeViewImpl.VIDEO_LIST_MASTER;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
public class PlayerActivityViewImpl extends BaseViewImpl implements PlayerActivityContract.View, Player.EventListener {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ArrayList<VideoResponseModel> videoResponseModelArrayList;
    private RecyclerView rvRelated;
    private TextView tvTitle, tvDesc;
    private PlayerActivityPresenterImpl playerActivityPresenter;
    HashMap<String, Integer> trackAndPositionMap;
    String strSelectedVideoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();

        VideoInfoDatabase videoInfoDatabase = VideoInfoDatabase.getVideoInfoDatabase(this);

        playerActivityPresenter = new PlayerActivityPresenterImpl(this
                , videoResponseModelArrayList
                , trackAndPositionMap
                , videoInfoDatabase.videoInfoModel());
        playerActivityPresenter.init();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        rvRelated = findViewById(R.id.rvRelated);
        rvRelated.setLayoutManager(new LinearLayoutManager(this));
        playerView = findViewById(R.id.exo_player);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);

        Intent intent = getIntent();
        if (intent != null
                && intent.getSerializableExtra(VIDEO_LIST_MASTER) != null
                && intent.getSerializableExtra(SELECTED_VIDEO_ID) != null
                && intent.getSerializableExtra(TRACK_LIST) != null) {

            trackAndPositionMap = (HashMap<String, Integer>) intent.getSerializableExtra(TRACK_LIST);
            strSelectedVideoId = intent.getStringExtra(SELECTED_VIDEO_ID);
            videoResponseModelArrayList = (ArrayList<VideoResponseModel>) intent.getSerializableExtra(VIDEO_LIST_MASTER);
            player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
            playerView.setPlayer(player);
        } else {
            Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initPlayerAndSetUI() {

        DefaultDataSourceFactory defaultDataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "video player"));

        ArrayList<ExtractorMediaSource> mediaSourceArrayList = new ArrayList<>();
        for (int i = 0; i < videoResponseModelArrayList.size(); i++) {

            VideoResponseModel videoResponseModel = videoResponseModelArrayList.get(i);

            ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory)
                    .createMediaSource(Uri.parse(videoResponseModel.getUrl()));
            mediaSourceArrayList.add(extractorMediaSource);
        }
        int playlistSize = mediaSourceArrayList.size();
        ConcatenatingMediaSource concatenatingMediaSource = new
                ConcatenatingMediaSource(mediaSourceArrayList.toArray(new MediaSource[playlistSize]));
        VideoInfo videoInfo = playerActivityPresenter.getVideoInfo(strSelectedVideoId);

        boolean haveStartPosition = false;
        if (videoInfo != null)
            haveStartPosition = !videoInfo.getIsEnded();
        if (haveStartPosition) {
            player.seekTo(0, videoInfo.getLastPositionMillis());
        }
        player.prepare(concatenatingMediaSource, !haveStartPosition, false);

        player.setPlayWhenReady(false);
        player.addListener(this);
        playerActivityPresenter.setUI(strSelectedVideoId);
    }

    @Override
    public void setUI(VideoResponseModel playingVideoModel, ArrayList<VideoResponseModel> remainingArrModels) {
        tvTitle.setText(playingVideoModel.getTitle());
        tvDesc.setText(playingVideoModel.getDescription());
        rvRelated.setAdapter(new RecentListAdapter(this, remainingArrModels, playerActivityPresenter));
    }

    @Override
    public void onClicks() {
        tvTitle.setOnClickListener(view -> playerActivityPresenter.expandOnclick(tvDesc.getMaxLines()));
    }

    @Override
    public void expandInfo() {
        tvDesc.setMaxLines(Integer.MAX_VALUE);
    }

    @Override
    public void collapseInfo() {
        tvDesc.setMaxLines(3);

    }

    @Override
    public void changeTrackOnSelect(int position
            , VideoResponseModel videoResponseModel
            , ArrayList<VideoResponseModel> remainingArrModels
            , boolean isEnded
            , long seekTo) {

        int pos = player.getCurrentWindowIndex();
        long currentMillis = player.getCurrentPosition();


        tvTitle.setText(videoResponseModel.getTitle());
        tvDesc.setText(videoResponseModel.getDescription());

        player.seekTo(position, isEnded && seekTo < 0 ? C.TIME_UNSET : seekTo);

        player.setPlayWhenReady(true);
        rvRelated.setAdapter(new RecentListAdapter(this, remainingArrModels, playerActivityPresenter));

        playerActivityPresenter.insertVideoInfo(pos, currentMillis, false);
        Toast.makeText(this, "position " + pos + " millis " + currentMillis, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        int trackPosition = player.getCurrentWindowIndex();
        if (reason == 0) {
            playerActivityPresenter.onTrackEnded(trackPosition);
            playerActivityPresenter.insertVideoInfo(trackPosition, player.getCurrentPosition(), true);

        } /*else {
            playerActivityPresenter.insertVideoInfo(trackPosition, player.getCurrentPosition(), false);
        }*/

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.getCurrentPosition();
        playerActivityPresenter.insertVideoInfo(player.getCurrentWindowIndex(), player.getCurrentPosition(), false);
        player.setPlayWhenReady(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }
}
