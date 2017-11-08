package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.LogoutController;
import com.inno.dabudabot.whyapp.core.logout.LogoutContract;
import com.inno.dabudabot.whyapp.core.logout.LogoutPresenter;
import com.inno.dabudabot.whyapp.ui.adapters.ChatUsersListingPagerAdapter;

/**
 * Created by Group-6 on 04.11.17.
 */

public class ChatUsersListingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
    private FloatingActionButton fab;
    private LogoutController logout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChatUsersListingActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, ChatUsersListingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_listing);
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
    }

    private void init() {
        // set on click event ЧТО ТУТ НЕ ТАК?
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
        ChatUsersListingPagerAdapter chatUsersListingPagerAdapter =
                new ChatUsersListingPagerAdapter(getSupportFragmentManager());
        mViewPagerUserListing.setAdapter(chatUsersListingPagerAdapter);

        // attach tab layout with view pager
        mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);

        logout = new LogoutController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout.performFirebaseLogout(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
