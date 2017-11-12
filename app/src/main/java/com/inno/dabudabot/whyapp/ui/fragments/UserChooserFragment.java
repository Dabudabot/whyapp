package com.inno.dabudabot.whyapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import com.inno.dabudabot.whyapp.listener.GetListingsView;
import com.inno.dabudabot.whyapp.ui.adapters.UserListingRecyclerAdapter;
import com.inno.dabudabot.whyapp.ui.dialogs.ChatsDialog;
import com.inno.dabudabot.whyapp.wrappers.BroadcastWrapper;
import com.inno.dabudabot.whyapp.wrappers.ForwardWrapper;
import com.inno.dabudabot.whyapp.wrappers.SelectChatWrapper;

import java.util.List;

import Util.Constants;
import Util.ItemClickSupport;
import Util.Settings;
import eventb_prelude.BSet;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;

/**
 * Created by dabudabot on 12.11.17.
 */

public class UserChooserFragment extends Fragment implements
        GetListingsView,
        ItemClickSupport.OnItemClickListener {

    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetListingsController getListingsController;

    private FloatingActionButton fab;
    private BSet<Integer> userIds;
    private Integer forwardingContent;
    private Content broadcastingContent;

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
        mRecyclerViewAllUserListing = (RecyclerView) view.findViewById(
                R.id.recycler_view_all_user_listing);
    }

    @Override
    public void onActivityCreated(
            @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListingsController.getChatsListing();
    }

    private void init() {
        getListingsController = new GetListingsController(this);
        getListingsController.getChatsListing();
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_move);
        forwardingContent = Settings.getInstance().getForwardingContent();
        broadcastingContent = Settings.getInstance().getBroadcastingContent();
        Settings.getInstance().setForwardingContent(null);
        Settings.getInstance().setBroadcastingContent(null);
        userIds = new BSet<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forwardingContent != null) {
                    new ForwardWrapper().runForward(
                            forwardingContent,
                            Settings.getInstance().getCurrentUser().getId(),
                            userIds,
                            Settings.getInstance().getMachine());
                }
                if (broadcastingContent != null) {
                    BroadcastWrapper broadcastWrapper = new BroadcastWrapper();
                    if (broadcastWrapper.guardBroadcast(
                            broadcastingContent.getId(),
                            Settings.getInstance().getCurrentUser().getId(),
                            userIds,
                            Settings.getInstance().getMachine())) {
                        broadcastWrapper.runBroadcast(
                                broadcastingContent.getId(),
                                Settings.getInstance().getCurrentUser().getId(),
                                userIds,
                                Settings.getInstance().getMachine());
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(Constants.NODE_CONTENTS)
                                .child(broadcastingContent.getId().toString())
                                .setValue(broadcastingContent);
                    }

                }
                UserChooserFragment.super.onStop();
            }
        });

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        userIds.add(mUserListingRecyclerAdapter.getUser(position).getId());
    }

    @Override
    public void onGetListingsSuccess(List<User> users) {
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetListingsFailure(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}