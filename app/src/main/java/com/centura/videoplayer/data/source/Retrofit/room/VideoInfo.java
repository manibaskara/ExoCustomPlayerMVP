package com.centura.videoplayer.data.source.Retrofit.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */
@Entity(tableName = "video_info")
public class VideoInfo {

    @PrimaryKey
    @NonNull
    private String videoId;
    private long lastPositionMillis;
    private boolean isEnded;

    public VideoInfo(@NonNull String videoId, long lastPositionMillis, boolean isEnded) {
        this.videoId = videoId;
        this.lastPositionMillis = lastPositionMillis;
        this.isEnded = isEnded;
    }

    @NonNull String getVideoId() {
        return videoId;
    }

    public void setVideoId(@NonNull String videoId) {
        this.videoId = videoId;
    }

    public long getLastPositionMillis() {
        return lastPositionMillis;
    }

    public void setLastPositionMillis(long lastPositionMillis) {
        this.lastPositionMillis = lastPositionMillis;
    }

    public boolean getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }
}
