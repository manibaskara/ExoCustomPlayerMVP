package com.centura.videoplayer.data.source.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */

@Dao
public interface VideoInfoDAO {

    @Query("SELECT * FROM video_info ORDER BY videoId ASC")
    List<VideoInfo> getAllVideoInfo();

    @Query("SELECT * FROM video_info WHERE videoId=:video_id")
    VideoInfo getVideoInfo(String video_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertVideoInfo(VideoInfo videoInfo);

    @Update
    int UpdateVideo(VideoInfo videoInfo);

    @Delete
    void deleteVideoInfo(VideoInfo videoInfo);

    @Query("DELETE FROM video_info")
    void deleteAll();


}
