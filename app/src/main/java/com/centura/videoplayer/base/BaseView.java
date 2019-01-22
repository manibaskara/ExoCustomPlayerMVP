package com.centura.videoplayer.base;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public interface BaseView {

    void init();

    void onClicks();

    void showProgress();

    void hideProgress();

    void showDialog(String message, Boolean flag);
}