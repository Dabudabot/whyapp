package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
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

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.auth.LogoutController;
import com.inno.dabudabot.whyapp.ui.adapters.ChatsListingAdapter;

/**
 * Created by Group-6 on 04.11.17.
 * User`s chats activity
 */

public class ChatsListingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
    private FloatingActionButton fab;
    private LogoutController logout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChatsListingActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, ChatsListingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
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
