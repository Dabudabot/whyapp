package com.inno.dabudabot.whyapp.wrappers;

import android.app.Activity;

import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;

import Util.Settings;
import Util.SimpleMapper;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.select_chat;

public class SelectChatWrapper extends select_chat {
    protected MachineWrapper machineWrapper;
    private Activity activity;
    
	public SelectChatWrapper(MachineWrapper m, Activity activity) {
		super(m);
		machineWrapper = m;
		this.activity = activity;
	}

	@Override
	public void run_select_chat(Integer sc_u1, Integer sc_u2) {
        if (super.guard_select_chat(sc_u1, sc_u2)) {
            super.run_select_chat(sc_u1, sc_u2);
            Settings.getInstance().setMyMachine(machineWrapper);
            SimpleMapper.toDatabaseReference(machineWrapper,
                    Settings.getInstance().getCurrentUser().getId());
            MessagingActivity.startActivity(activity, sc_u2);
        }
	}
}
