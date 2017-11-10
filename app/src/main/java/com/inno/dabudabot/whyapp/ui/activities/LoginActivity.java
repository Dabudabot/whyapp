package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.fragments.LoginFragment;

/**
 * Created by Group-6 on 10/21/17.
 * Login and registration activity
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * current activity startup as intent
     * @param context caller of this intent
     */
    public static void startIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * current activity startup as intent with flag
     * @param context caller of this intent
     * @param flags flag state to this intent
     */
    public static void startIntent(Context context, int flags) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    /**
     * Runs at creation of current activity
     * sets content and fragments
     * @param savedInstanceState default state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * init fragment
     */
    private void init() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // set the toolbar
        setSupportActionBar(mToolbar);

        // set the login screen fragment
        FragmentTransaction fragmentTransaction
                = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_login,
                new LoginFragment(),
                LoginFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
