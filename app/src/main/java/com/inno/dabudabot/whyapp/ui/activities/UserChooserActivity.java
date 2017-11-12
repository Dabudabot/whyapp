package com.inno.dabudabot.whyapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.adapters.UserChooserAdapter;


/**
 * Created by dabudabot on 12.11.17.
 */

public class UserChooserActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserChooserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chooser);
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

        mToolbar.setTitle(R.string.user_chooser);

        // set the view pager adapter
        UserChooserAdapter userChooserAdapter =
                new UserChooserAdapter(getSupportFragmentManager());
        mViewPagerUserListing.setAdapter(userChooserAdapter);

        // attach tab layout with view pager
        mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);
    }
}