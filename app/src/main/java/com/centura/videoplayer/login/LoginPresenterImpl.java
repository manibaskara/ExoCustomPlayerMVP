package com.centura.videoplayer.login;

import android.content.Intent;
import android.util.Log;

import com.centura.videoplayer.data.source.AppRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public class LoginPresenterImpl implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;

    LoginPresenterImpl(LoginContract.View mLoginView, GoogleSignInClient googleSignInClient
            , FirebaseAuth mAuth) {
        this.mLoginView = mLoginView;
        this.googleSignInClient = googleSignInClient;
        this.mAuth = mAuth;
    }


    @Override
    public void isLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            mLoginView.displayUser(mAuth.getCurrentUser().getDisplayName());
        } else {
            mLoginView.enableSignIn();
        }
    }

    @Override
    public void onLoginButtonOnClicked() {

        if (mAuth.getCurrentUser() != null) {
            if (mAuth.getCurrentUser().getDisplayName() != null && !mAuth.getCurrentUser().getDisplayName().isEmpty()) {
                mLoginView.showHomeActivity(mAuth.getCurrentUser().getDisplayName());
            }
        } else
            mLoginView.performLogin();
    }

    @Override
    public void processGoogleResult(Intent data) {
        mLoginView.showProgress();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = null;
            if (account != null)
                credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mLoginView.hideProgress();
            if (credential != null) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                mLoginView.hideProgress();
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null && user.getDisplayName() != null)
                                    mLoginView.showHomeActivity(user.getDisplayName());

                                else {
                                    mLoginView.displayUser("Sign in Error");
                                    mLoginView.enableSignIn();
                                }
                            } else {
                                mLoginView.displayUser("Sign in Error");
                                mLoginView.enableSignIn();
                            }
                        });
            }

        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            mLoginView.hideProgress();
            mLoginView.displayUser("Google sign in failed");
            mLoginView.enableSignIn();
            Log.w("", "Google sign in failed", e);

        }
    }

    @Override
    public void signOut() {
        mAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(
                task -> mLoginView.onSignOutSuccess());

    }

    @Override
    public void init() {

    }
}
