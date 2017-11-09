package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.unselect_chat;

public class UnselectChatWrapper extends unselect_chat {
	protected MachineWrapper machineWrapper;

	public UnselectChatWrapper(MachineWrapper m) {
		super(m);
		machineWrapper = m;
	}
}
