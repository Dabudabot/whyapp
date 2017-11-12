package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.unselect_chat;

public class UnselectChatWrapper {

	public UnselectChatWrapper() {}

	public boolean guardUnselectChat(Integer usc_u1, Integer usc_u2, machine3 m) {
        return new unselect_chat(m).guard_unselect_chat(usc_u1, usc_u2);
    }

    public void runUnselectChat(Integer usc_u1, Integer usc_u2, machine3 m) {
	    unselect_chat unselect_chat = new unselect_chat(m);
	    if (unselect_chat.guard_unselect_chat(usc_u1, usc_u2)) {
	        Settings.getInstance().setBusy(true);
            unselect_chat.run_unselect_chat(usc_u1, usc_u2);
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
        }
    }
}
