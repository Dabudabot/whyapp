package com.inno.dabudabot.whyapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.fragments.UsersFragment;
import com.inno.dabudabot.whyapp.utils.Constants;

/**
 * Created by Group-6 on 04.11.17.
 */

public class ChatUsersListingPagerAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments = new Fragment[]{UsersFragment.newInstance(Constants.TYPE_CHAT)};
    private static final String[] sTitles = new String[]{"Chats"};

    public ChatUsersListingPagerAdapter(FragmentManager fm) {
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
