package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.machine3;
import group_6_model_sequential.unmute_chat;

public class UnmuteChatWrapper {

	public UnmuteChatWrapper() {}

	public void runUnmuteChat(Integer umc_u1, Integer umc_u2, machine3 m) {
	    unmute_chat unmute_chat = new unmute_chat(m);
		if (unmute_chat.guard_unmute_chat(umc_u1, umc_u2)) {
			Settings.getInstance().setBusy(true);
            unmute_chat.run_unmute_chat(umc_u1, umc_u2);
			Settings.getInstance().commitMachine(m);
			Settings.getInstance().setBusy(false);
		}
	}
}
