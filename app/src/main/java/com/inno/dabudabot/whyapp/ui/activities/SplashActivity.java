package com.inno.dabudabot.whyapp.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.inno.dabudabot.whyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.inno.dabudabot.whyapp.controller.sync.InitListeners;
import com.inno.dabudabot.whyapp.listener.InitListenersView;

/**
 * Created by Group-6 on 10/21/17.
 *
 * It is possible to say that work starts here
 *
 * Checking is user logged in or not
 * if logged in redirect the user to user listing activity
 * if not redirect user to login activity
 */
public class SplashActivity extends AppCompatActivity implements InitListenersView {
    private static final int SPLASH_TIME_MS = 2000;

    /**
     * Runs at creation of current activity
     * @param savedInstanceState default state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mHandler = new Handler();
        final InitListeners initListeners = new InitListeners(this);

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    initListeners.init();
                } else {
                    LoginActivity.startIntent(
                            SplashActivity.this);
                }
                finish();
            }
        };

        mHandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }

    @Override
    public void onInitSuccess() {
        ChatsListingActivity.startActivity(SplashActivity.this);
    }

    @Override
    public void onInitFailure() {
        LoginActivity.startIntent(SplashActivity.this);
    }
}
