package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import Util.SimpleMapper;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.unselect_chat;

public class UnselectChatWrapper extends unselect_chat {
	protected MachineWrapper machineWrapper;

	public UnselectChatWrapper(MachineWrapper m) {
		super(m);
		machineWrapper = m;
	}

    @Override
    public void run_unselect_chat(Integer usc_u1, Integer usc_u2) {
	    if (super.guard_unselect_chat(usc_u1, usc_u2)) {
            super.run_unselect_chat(usc_u1, usc_u2);
            Settings.getInstance().setMyMachine(machineWrapper);
            SimpleMapper.toDatabaseReference(machineWrapper,
                    Settings.getInstance().getCurrentUser().getId());
        }
    }
}
