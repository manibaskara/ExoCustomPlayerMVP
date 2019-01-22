package com.centura.videoplayer.loginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.centura.videoplayer.base.BaseViewImpl;
import com.centura.videoplayer.R;
import com.centura.videoplayer.videoListActivity.HomeViewImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public class LoginViewImpl extends BaseViewImpl implements LoginContract.View {

    private LoginContract.Presenter mLoginPresenter;
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;
    private Button btnSignOut, btnEnter, signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        onClicks();

    }

    @Override
    public void init() {
        signInButton = findViewById(R.id.signInButton);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnEnter = findViewById(R.id.btnEnter);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_1))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mLoginPresenter = new LoginPresenterImpl(this, mGoogleSignInClient, mAuth);
        mLoginPresenter.isLoggedIn();

    }

    @Override
    public void onClicks() {

        signInButton.setOnClickListener(view -> mLoginPresenter.onLoginButtonOnClicked());

        btnEnter.setOnClickListener(view -> mLoginPresenter.onLoginButtonOnClicked());

        btnSignOut.setOnClickListener(view -> mLoginPresenter.signOut());
    }

    @Override
    public void showHomeActivity(String userName) {
        Intent intent = new Intent(this, HomeViewImpl.class);
        intent.putExtra("user_name", userName);
        startActivity(intent);
        finish();
    }

    @Override
    public void performLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onSignOutSuccess() {
        Toast.makeText(this, "Signed Out Successfully.", Toast.LENGTH_SHORT).show();
        signInButton.setVisibility(View.VISIBLE);
        btnSignOut.setVisibility(View.INVISIBLE);
        btnEnter.setVisibility(View.GONE);
    }

    @Override
    public void displayUser(String name) {
        btnEnter.setText(String.format("Enter as %s", name));
        signInButton.setVisibility(View.INVISIBLE);
        btnSignOut.setVisibility(View.VISIBLE);
        btnEnter.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableSignIn() {
        signInButton.setVisibility(View.VISIBLE);
        btnSignOut.setVisibility(View.INVISIBLE);
        btnEnter.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mLoginPresenter.processGoogleResult(data);
        }
    }
}