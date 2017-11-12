package com.inno.dabudabot.whyapp.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.SendContentController;
import com.inno.dabudabot.whyapp.controller.auth.LogoutController;
import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import com.inno.dabudabot.whyapp.controller.sync.InitListeners;
import com.inno.dabudabot.whyapp.listener.GetListingsView;
import com.inno.dabudabot.whyapp.listener.SendContentView;
import com.inno.dabudabot.whyapp.ui.adapters.ChatsListingAdapter;
import com.inno.dabudabot.whyapp.ui.dialogs.BroadcastDialog;
import com.inno.dabudabot.whyapp.wrappers.BroadcastWrapper;
import com.inno.dabudabot.whyapp.wrappers.ForwardWrapper;

import java.util.List;

import Util.Constants;
import Util.Settings;
import eventb_prelude.BSet;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;

/**
 * Created by Group-6 on 04.11.17.
 * User`s chats activity
 */

public class ChatsListingActivity extends AppCompatActivity implements GetListingsView, SendContentView {
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
    private FloatingActionButton fab;
    private LogoutController logout;
    private GetListingsController getListingsController;
    private SendContentController sendContentController;
    private BSet<Integer> userIds;

    public static void onInitSuccess(Context context) {
        Intent intent = new Intent(context, ChatsListingActivity.class);
        context.startActivity(intent);
    }

    public static void onInitSuccess(Context context, int flags) {
        Intent intent = new Intent(context, ChatsListingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    public static void startActivity(Context context) {
        InitListeners initListeners = new InitListeners();
        initListeners.init(context);
    }

    public static void startActivity(Context context, int flags) {
        InitListeners initListeners = new InitListeners();
        initListeners.init(context, flags);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_listing);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayoutUserListing = (TabLayout)
                findViewById(R.id.tab_layout_chat_user_listing);
        mViewPagerUserListing = (ViewPager)
                findViewById(R.id.view_pager_chat_user_listing);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        getListingsController = new GetListingsController(this);
        sendContentController = new SendContentController(this);
    }

    private void init() {
        // set on click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListingActivity.startActivity(v.getContext());
            }
        });

        // set the toolbar
        setSupportActionBar(mToolbar);

        mToolbar.setTitle(R.string.chats_name);

        // set the view pager adapter
        ChatsListingAdapter chatsListingAdapter =
                new ChatsListingAdapter(getSupportFragmentManager());
        mViewPagerUserListing.setAdapter(chatsListingAdapter);

        // attach tab layout with view pager
        mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);

        logout = new LogoutController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout.performFirebaseLogout(this);
                break;
            case R.id.action_broadcast:
                getListingsController.getUsersListing();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetListingsSuccess(List<User> users) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatsListingActivity.this);

        final List<User> userList = users;
        String[] names = new String[users.size()];
        final boolean[] checkedNames = new boolean[users.size()];
        int i = 0;
        for (User user : userList) {
            names[i] = user.getName();
            checkedNames[i] = false;
            i++;
        }

        builder.setMultiChoiceItems(names, checkedNames,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Update the current focused item's checked status
                        checkedNames[which] = isChecked;
                    }
                });

        final EditText input = new EditText(ChatsListingActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("User chooser");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                String broadcastingMessage = input.getText().toString();

                userIds = new BSet<>();
                for (int i = 0; i < checkedNames.length; i++) {
                    if (checkedNames[i]) {
                        userIds.add(userList.get(i).getId());
                    }
                }

                sendContentController.send(broadcastingMessage, getParent());
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Settings.getInstance().setBroadcastingContent(null);
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    @Override
    public void onGetListingsFailure(String message) {

    }

    @Override
    public void sendSuccess(Content content) {
        new BroadcastWrapper().runBroadcast(
                content.getId(),
                Settings.getInstance().getCurrentUser().getId(),
                userIds,
                Settings.getInstance().getMachine());
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_CONTENTS)
                .child(content.getId().toString())
                .setValue(content);
    }

    @Override
    public void sendFailure(String message) {

    }
}
