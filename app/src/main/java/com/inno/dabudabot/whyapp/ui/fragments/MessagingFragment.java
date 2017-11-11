package com.inno.dabudabot.whyapp.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.ReceiveContentController;
import com.inno.dabudabot.whyapp.controller.SendContentController;

import Util.Settings;

import com.inno.dabudabot.whyapp.listener.ReceiveContentView;
import com.inno.dabudabot.whyapp.listener.SendContentView;
import com.inno.dabudabot.whyapp.ui.adapters.MessagingRecyclerAdapter;
import com.inno.dabudabot.whyapp.wrappers.UnselectChatWrapper;

import java.util.ArrayList;

import Util.Constants;
import eventb_prelude.Pair;
import group_6_model_sequential.Content;


/**
 * Created by Group-6 on 09.11.17.
 * Messaging fragment
 */
public class MessagingFragment
        extends Fragment
        implements SendContentView,
        ReceiveContentView,
        TextView.OnEditorActionListener {

    private EditText mETxtMessage;
    private ProgressDialog mProgressDialog;

    private SendContentController sendContentController;
    private UnselectChatWrapper unselectChatWrapper;

    private RecyclerView mRecyclerViewChat;
    private MessagingRecyclerAdapter mMessagingRecyclerAdapter;

    public static MessagingFragment newInstance(Integer id) {
        Bundle args = new Bundle();
        args.putInt(Constants.NODE_ID, id);
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        if (unselectChatWrapper.guard_unselect_chat(
                Settings.getInstance().getCurrentUser().getId(),
                getArguments().getInt(Constants.NODE_ID))) {
            unselectChatWrapper.run_unselect_chat(
                    Settings.getInstance().getCurrentUser().getId(),
                    getArguments().getInt(Constants.NODE_ID));
            ReceiveContentController.unsubscribe(this);
            super.onStop();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(
                R.layout.fragment_messaging,
                container,
                false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mRecyclerViewChat = (RecyclerView) view.findViewById(R.id.recycler_view_chat);
        mETxtMessage = (EditText) view.findViewById(R.id.edit_text_message);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mETxtMessage.setOnEditorActionListener(this);
        sendContentController = new SendContentController(this);
        unselectChatWrapper = new UnselectChatWrapper(Settings.getInstance().getMergedMachine());

        ReceiveContentController.subscribe(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    private void sendMessage() {
        String message = mETxtMessage.getText().toString();
        sendContentController.send(message,
                Settings.getInstance().getCurrentUser().getId(),
                getArguments().getInt(Constants.NODE_ID),
                getActivity());
    }

    @Override
    public void sendSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFailure(String message) {
        System.err.println("Messaging - BAD");
    }

    @Override
    public void onReceiveSuccess() {
        mMessagingRecyclerAdapter = new MessagingRecyclerAdapter(new ArrayList<Content>());
        mRecyclerViewChat.setAdapter(mMessagingRecyclerAdapter);

        for (Pair<Integer, Integer> m
                : Settings.getInstance().getMyMachine().get_readChatContentSeq()) {
            mMessagingRecyclerAdapter.add(m.snd());
            mRecyclerViewChat.smoothScrollToPosition(mMessagingRecyclerAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onReceiveFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Integer getSender() {
        return Settings.getInstance().getCurrentUser().getId();
    }

    @Override
    public Integer getReceiver() {
        return getArguments().getInt(Constants.NODE_ID);
    }

    //TODO 8 SELECT MESSAGE DROP MENU - FORWARD\BROADCAST and DELETE
}
