package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.inno.dabudabot.whyapp.WhyMainApp;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.auth.LogoutController;
import com.inno.dabudabot.whyapp.ui.fragments.MessagingFragment;
import com.inno.dabudabot.whyapp.wrappers.DeleteChatSessionWrapper;
import com.inno.dabudabot.whyapp.wrappers.MuteChatWrapper;
import com.inno.dabudabot.whyapp.wrappers.UnmuteChatWrapper;

import Util.Constants;
import Util.Settings;

/**
 * Created by Group-6 on 10/21/17.
 * Chatting screen activity
 */
public class MessagingActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private LogoutController logoutController;

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
        logoutController = new LogoutController();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general3, menu);
        boolean muted = new MuteChatWrapper().guardMuteChat(
                getIntent().getExtras().getInt(Constants.NODE_ID),
                Settings.getInstance().getCurrentUser().getId(),
                Settings.getInstance().getMachine());
        boolean unmuted = new UnmuteChatWrapper().guardUnmuteChat(
                getIntent().getExtras().getInt(Constants.NODE_ID),
                Settings.getInstance().getCurrentUser().getId(),
                Settings.getInstance().getMachine());
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getTitle().equals("Mute")
                    && unmuted) {
                menu.getItem(i).setTitle("Unmute");
            } else if (menu.getItem(i).getTitle().equals("Unmute")
                    && muted) {
                menu.getItem(i).setTitle("Mute");
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_muteunmute:
                MuteChatWrapper muteChatWrapper = new MuteChatWrapper();
                UnmuteChatWrapper unmuteChatWrapper = new UnmuteChatWrapper();
                if (item.getTitle().equals("Mute") &&
                        muteChatWrapper.guardMuteChat(
                                getIntent().getExtras().getInt(Constants.NODE_ID),
                                Settings.getInstance().getCurrentUser().getId(),
                                Settings.getInstance().getMachine())) {
                    muteChatWrapper.runMuteChat(
                            getIntent().getExtras().getInt(Constants.NODE_ID),
                            Settings.getInstance().getCurrentUser().getId(),
                            Settings.getInstance().getMachine());
                    item.setTitle("Unmute");
                } else if (item.getTitle().equals("Unmute")
                        && unmuteChatWrapper.guardUnmuteChat(getIntent().getExtras().getInt(Constants.NODE_ID),
                        Settings.getInstance().getCurrentUser().getId(),
                        Settings.getInstance().getMachine())) {
                    unmuteChatWrapper.runUnmuteChat(
                            getIntent().getExtras().getInt(Constants.NODE_ID),
                            Settings.getInstance().getCurrentUser().getId(),
                            Settings.getInstance().getMachine());
                    item.setTitle("Mute");
                }
                break;
            case R.id.action_delete_chat:
                new DeleteChatSessionWrapper().runDeleteChatSession(
                        Settings.getInstance().getCurrentUser().getId(),
                        getIntent().getExtras().getInt(Constants.NODE_ID),
                        Settings.getInstance().getMachine());
                ChatsListingActivity.startActivity(MessagingActivity.this,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.action_logout:
                logoutController.performFirebaseLogout(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
