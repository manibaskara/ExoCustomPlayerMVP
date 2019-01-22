package com.centura.videoplayer.loginActivity;

import android.content.Intent;

import com.centura.videoplayer.base.BasePresenter;
import com.centura.videoplayer.base.BaseView;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public interface LoginContract {

    interface View extends BaseView {
        void showHomeActivity(String userName);

        void performLogin();

        void onSignOutSuccess();

        void displayUser(String name);

        void enableSignIn();

    }

    interface Presenter extends BasePresenter {

        void isLoggedIn();

        void onLoginButtonOnClicked();

        void processGoogleResult(Intent data);

        void signOut();
    }


}
