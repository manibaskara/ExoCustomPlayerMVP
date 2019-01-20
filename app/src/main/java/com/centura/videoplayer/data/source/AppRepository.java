package com.centura.videoplayer.data.source;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public class AppRepository implements AppDataSource {

    private AppDataSource mSharedPrefsDataSource;

    public AppRepository(AppDataSource mSharedPrefsDataSource) {
       this.mSharedPrefsDataSource = mSharedPrefsDataSource;
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        mSharedPrefsDataSource.saveIsLoggedIn(isLoggedIn);
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPrefsDataSource.isLoggedIn();
    }
}
