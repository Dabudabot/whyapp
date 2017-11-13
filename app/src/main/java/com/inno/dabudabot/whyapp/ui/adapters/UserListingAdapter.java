package com.inno.dabudabot.whyapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inno.dabudabot.whyapp.ui.fragments.UsersListingFragment;

/**
 * Created by Group-6 on 04.11.17.
 * Inflates view with all user
 */
public class UserListingAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments =
            new Fragment[]{new UsersListingFragment()};
    private static final String[] sTitles = new String[]{"Users"};

    public UserListingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return sFragments[position];
    }

    @Override
    public int getCount() {
        return sFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTitles[position];
    }
}
