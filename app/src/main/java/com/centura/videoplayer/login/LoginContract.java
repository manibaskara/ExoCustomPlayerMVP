package com.centura.videoplayer.login;

import android.content.Context;
import android.content.Intent;

import com.centura.videoplayer.BasePresenter;
import com.centura.videoplayer.BaseView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
