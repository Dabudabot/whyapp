package com.inno.dabudabot.whyapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.inno.dabudabot.whyapp.ui.dialogs.ChatsDialog;
import com.inno.dabudabot.whyapp.wrappers.SelectChatWrapper;

import Util.ItemClickSupport;

import java.util.List;

/**
 * Created by Group-6 on 07.11.17.
 * List of users with whom current user have chat
 */
public class ChatsListingFragment extends Fragment implements
        GetListingsView,
        ItemClickSupport.OnItemClickListener,
        ItemClickSupport.OnItemLongClickListener {

    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetListingsController getListingsController;

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

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        new SelectChatWrapper().runSelectChat(
                Settings.getInstance().getCurrentUser().getId(),
                mUserListingRecyclerAdapter.getUser(position).getId(),
                Settings.getInstance().getMachine(),
                getActivity());
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

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        ChatsDialog cdd =
                new ChatsDialog(getActivity(),
                        mUserListingRecyclerAdapter.getUser(position).getId());
        cdd.show();
        return true;
    }
}
