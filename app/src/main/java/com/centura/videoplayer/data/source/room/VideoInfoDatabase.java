package com.centura.videoplayer.data.source.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */

@Database(entities = {VideoInfo.class}, version = 1)
public abstract class VideoInfoDatabase extends RoomDatabase {

    private static VideoInfoDatabase INSTANCE;

    public abstract VideoInfoDAO videoInfoModel();

    public static VideoInfoDatabase getVideoInfoDatabase(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(), VideoInfoDatabase.class, "video_info")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
