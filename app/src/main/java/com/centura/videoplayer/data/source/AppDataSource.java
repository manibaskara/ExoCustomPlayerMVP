package com.centura.videoplayer.data.source;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public interface AppDataSource {

    void saveIsLoggedIn(boolean isLoggedIn);

    boolean isLoggedIn();
}