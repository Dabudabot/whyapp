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
import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import com.inno.dabudabot.whyapp.listener.GetListingsView;

import Util.Settings;
import group_6_model_sequential.User;

import com.inno.dabudabot.whyapp.ui.adapters.UserListingRecyclerAdapter;
import com.inno.dabudabot.whyapp.wrappers.CreateChatSessionWrapper;

import Util.ItemClickSupport;

import java.util.List;

/**
 * Created by Group-6 on 07.11.17.
 * List of users
 */
public class UsersListingFragment extends Fragment implements
        GetListingsView,
        ItemClickSupport.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetListingsController getListingsController;
    private CreateChatSessionWrapper createChatSessionWrapper;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(
                        R.layout.fragment_listing,
                        container,
                        false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
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
        createChatSessionWrapper = new CreateChatSessionWrapper();
        getListingsController = new GetListingsController(this);
        getListingsController.getUsersListing();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                //swipeRefreshLayout.setRefreshing(true);
            }
        });
        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        createChatSessionWrapper.runCreateChatSession(
                Settings.getInstance().getCurrentUser().getId(),
                mUserListingRecyclerAdapter.getUser(position).getId(),
                Settings.getInstance().getMachine(),
                getActivity()
        );
    }

    @Override
    public void onGetListingsSuccess(List<User> users) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetListingsFailure(String message) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(
                getActivity(),
                "Error: " + message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getListingsController.getUsersListing();
    }
}
