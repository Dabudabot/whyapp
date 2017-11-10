package com.inno.dabudabot.whyapp.ui.fragments;

import android.app.Activity;
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

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.auth.GenerateIdController;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;
import group_6_model_sequential.Content;
import com.inno.dabudabot.whyapp.ui.adapters.MessagingRecyclerAdapter;
import Util.Constants;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Group-6 on 09.11.17.
 * Messaging fragment
 */
public class MessagingFragment
        extends Fragment
        implements GenerateIdView,
        TextView.OnEditorActionListener {

    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ProgressDialog mProgressDialog;
    private MessagingRecyclerAdapter mMessagingRecyclerAdapter;

    private GenerateIdController generateIdController;

    public static MessagingFragment newInstance(Integer id) {
        Bundle args = new Bundle();
        args.putInt(Constants.NODE_ID, id);
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        generateIdController = new GenerateIdController(this);

        //TODO 7 чтение сообщений в ресайклер
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
        Integer id = getArguments().getInt(Constants.NODE_ID);
        generateIdController.checkInDatabase(
                getActivity(),
                message,
                Constants.NODE_CONTENTS);
    }

    @Override
    public void onGenerateSuccess(Activity activity,
                                  Object target,
                                  Integer id) {
        if (target instanceof String) {
            Content chatMessage = new Content(id,
                    (String) target,
                    System.currentTimeMillis());
            //sendContentController.send(chatMessage);
            //TODO 6 SEND MESSAGE
        } else {
            onGenerateFailure("BAD TYPE");
        }
    }

    @Override
    public void onGenerateFailure(String message) {

    }

    //TODO 8 SELECT MESSAGE DROP MENU - FORWARD\BROADCAST and DELETE
}
