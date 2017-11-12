package com.inno.dabudabot.whyapp.wrappers;

import android.app.Activity;

import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.create_chat_session;

public class CreateChatSessionWrapper {

    public CreateChatSessionWrapper() {}

    public void runCreateChatSession(Integer ccs_u1, Integer ccs_u2, machine3 m, Activity a) {
        create_chat_session create_chat_session = new create_chat_session(m);
        if (create_chat_session.guard_create_chat_session(ccs_u1, ccs_u2)) {
            Settings.getInstance().setBusy(true);
            create_chat_session.run_create_chat_session(ccs_u1, ccs_u2);
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
            MessagingActivity.startActivity(a, ccs_u2);
        }
    }
}