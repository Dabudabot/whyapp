package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.inno.dabudabot.whyapp.WhyMainApp;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.fragments.MessagingFragment;
import Util.Constants;
import Util.Settings;

/**
 * Created by Group-6 on 10/21/17.
 * Chatting screen activity
 */
public class MessagingActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    public static void startActivity(Context context, Integer userId) {
        Intent intent = new Intent(context, MessagingActivity.class);
        intent.putExtra(Constants.NODE_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // set the toolbar
        setSupportActionBar(mToolbar);

        // set toolbar title
        mToolbar.setTitle(Settings.getInstance().getUsers().get(
                getIntent().getExtras().getInt(Constants.NODE_ID)).getName());

        // set the register screen fragment
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat,
                MessagingFragment.newInstance(
                        getIntent().getExtras().getInt(Constants.NODE_ID)),
                MessagingFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WhyMainApp.setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WhyMainApp.setChatActivityOpen(false);
    }

    //TODO 10 add menu with mute unmute and delete

    //TODO 11 muted sign
}
