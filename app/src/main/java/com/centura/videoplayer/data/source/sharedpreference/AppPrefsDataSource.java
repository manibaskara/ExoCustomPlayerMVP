package com.centura.videoplayer.data.source.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.centura.videoplayer.data.source.AppDataSource;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public class AppPrefsDataSource implements AppDataSource {

    private SharedPreferences mSharedPreferences;

    public AppPrefsDataSource(Context context) {
        mSharedPreferences = context.getSharedPreferences("MVPDemo", Context.MODE_PRIVATE);
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        mSharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply();
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean("isLoggedIn", false);
    }
}
