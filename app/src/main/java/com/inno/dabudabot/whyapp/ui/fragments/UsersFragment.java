package com.inno.dabudabot.whyapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.ChatCreatorController;
import com.inno.dabudabot.whyapp.controller.GetUsersController;
import com.inno.dabudabot.whyapp.listener.GetUsersView;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.ui.adapters.UserListingRecyclerAdapter;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.ItemClickSupport;

import java.util.List;

public class UsersFragment extends Fragment implements
        GetUsersView,
        ItemClickSupport.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetUsersController getUsersController;
    private ChatCreatorController chatCreatorController;

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
        chatCreatorController = new ChatCreatorController();
        getUsersController = new GetUsersController(this);
        getUsersController.getUsersFromFirebase();
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
        getUsersController.getUsersFromFirebase();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        chatCreatorController.initCreate(getActivity(),
                mUserListingRecyclerAdapter.getUser(position).getId());
    }

    @Override
    public void onGetUsersSuccess(List<User> users) {
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
    public void onGetUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(
                getActivity(),
                "Error: " + message,
                Toast.LENGTH_SHORT).show();
    }
}
