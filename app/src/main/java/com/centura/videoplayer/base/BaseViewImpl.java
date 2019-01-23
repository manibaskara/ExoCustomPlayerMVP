package com.centura.videoplayer.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Manikandan Baskaran on 21-01-2019.
 */
public abstract class BaseViewImpl extends AppCompatActivity implements BaseView {

    private boolean progressAlive = false;
    private ProgressDialog progressDialog;

    @Override
    public void init() {

    }

    @Override
    public void onClicks() {

    }

    @Override
    public void showDialog(String message, Boolean flag) {

        if (flag) {
            if (progressAlive) {
                try {
                    progressDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressAlive = false;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressAlive = true;
            progressDialog.show();
        } else {
            if (progressAlive) {
                progressDialog.dismiss();
                progressDialog.cancel();
                progressAlive = false;
            }
        }
    }
}
