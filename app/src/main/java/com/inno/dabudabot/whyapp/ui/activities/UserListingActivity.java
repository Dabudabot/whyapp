package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.auth.LogoutController;
import com.inno.dabudabot.whyapp.ui.adapters.UserListingAdapter;

/**
 * Created by Group-6 on 09.11.17.
 * All users activity
 */
public class UserListingActivity
        extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;

    private LogoutController logoutController;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserListingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listing);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayoutUserListing = (TabLayout) findViewById(R.id.tab_layout_user_listing);
        mViewPagerUserListing = (ViewPager) findViewById(R.id.view_pager_user_listing);
    }

    private void init() {
        // set the toolbar
        setSupportActionBar(mToolbar);

        mToolbar.setTitle(R.string.contacts_name);

        // set the view pager adapter
        UserListingAdapter userListingAdapter =
                new UserListingAdapter(getSupportFragmentManager());
        mViewPagerUserListing.setAdapter(userListingAdapter);

        // attach tab layout with view pager
        mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);

        logoutController = new LogoutController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutController.performFirebaseLogout(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
