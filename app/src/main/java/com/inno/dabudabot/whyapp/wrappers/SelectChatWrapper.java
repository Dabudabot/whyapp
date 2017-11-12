package com.inno.dabudabot.whyapp.wrappers;

import android.app.Activity;

import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;

import Util.Settings;
import Util.SimpleMapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.machine3;
import group_6_model_sequential.select_chat;

public class SelectChatWrapper {
    
	public SelectChatWrapper() {}

	public void runSelectChat(Integer sc_u1, Integer sc_u2, machine3 m, Activity a) {
	    select_chat select_chat = new select_chat(m);
        if (select_chat.guard_select_chat(sc_u1, sc_u2)) {
            Settings.getInstance().setBusy(true);
            select_chat.run_select_chat(sc_u1, sc_u2);
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
            MessagingActivity.startActivity(a, sc_u2);
        }
	}
}
