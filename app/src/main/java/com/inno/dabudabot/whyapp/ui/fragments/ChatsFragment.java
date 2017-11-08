package com.inno.dabudabot.whyapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.listener.GetUsersView;
import com.inno.dabudabot.whyapp.core.users.getall.GetChatUsersPresenter;
import com.inno.dabudabot.whyapp.core.users.getall.GetUsersPresenter;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;
import com.inno.dabudabot.whyapp.ui.adapters.UserListingRecyclerAdapter;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by dabudabot on 07.11.17.
 */

public class ChatsFragment extends Fragment implements
        GetUsersView,
        ItemClickSupport.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetUsersPresenter mGetUsersPresenter;
    private GetChatUsersPresenter mGetChatUsersPresenter;

    public static UsersFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constants.ARG_TYPE, type);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(
                        R.layout.fragment_users,
                        container,
                        false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(
                R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = (RecyclerView) view.findViewById(
                R.id.recycler_view_all_user_listing);
    }

    @Override
    public void onActivityCreated(
            @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetUsersPresenter = new GetUsersPresenter(this);//???
        mGetChatUsersPresenter = new GetChatUsersPresenter(this);//???
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        if (TextUtils.equals(
                getArguments().getString(Constants.ARG_TYPE),
                Constants.TYPE_CHAT)) {
            mGetChatUsersPresenter.getChatUsers();
        } else if (TextUtils.equals(
                getArguments().getString(Constants.ARG_TYPE),
                Constants.TYPE_USER)) {
            mGetUsersPresenter.getAllUsers();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        MessagingActivity.startActivity(getActivity(),
                mUserListingRecyclerAdapter.getUser(position).getEmail(),
                mUserListingRecyclerAdapter.getUser(position).getUid(),
                mUserListingRecyclerAdapter.getUser(position).getFirebaseToken());
    }




    //----------------
    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetChatUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
