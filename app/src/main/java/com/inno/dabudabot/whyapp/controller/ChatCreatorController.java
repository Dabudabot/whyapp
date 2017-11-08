package com.inno.dabudabot.whyapp.controller;

import android.app.Activity;

import com.inno.dabudabot.whyapp.listener.SyncView;
import com.inno.dabudabot.whyapp.models.MachineWrapper;
import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;
import com.inno.dabudabot.whyapp.utils.Settings;

import WhyApp_Model_Group_6_sequential.create_chat_session;
import WhyApp_Model_Group_6_sequential.machine3;

/**
 * Created by dabudabot on 07.11.17.
 */

public class ChatCreatorController implements SyncView {

    private machine3 mac;
    private create_chat_session createChatSession;
    private SyncController syncController;

    public ChatCreatorController() {
        mac = MachineWrapper.getInstance();
        createChatSession = new create_chat_session(mac);
        syncController = new SyncController(this);
    }

    public void initCreate(Activity activity,
                           Integer userId) {
        Integer currentId = Settings.getInstance().getCurrentId();
        createChatSession.run_create_chat_session(currentId, userId);
        syncController.sync(activity, userId);
    }

    @Override
    public void onSyncSuccess(Activity activity,
                              Integer userId) {
        MessagingActivity.startActivity(activity, userId);
    }

    @Override
    public void onSyncFailure() {

    }
}