package com.inno.dabudabot.whyapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inno.dabudabot.whyapp.ui.fragments.UserChooserFragment;

/**
 * Created by dabudabot on 12.11.17.
 */

public class UserChooserAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments =
            new Fragment[]{new UserChooserFragment()};
    private static final String[] sTitles = new String[]{"UserChooser"};

    public UserChooserAdapter(FragmentManager fm) {
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