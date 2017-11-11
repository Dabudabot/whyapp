package com.inno.dabudabot.whyapp.wrappers;

import android.app.Activity;

import com.inno.dabudabot.whyapp.ui.activities.MessagingActivity;

import Util.Settings;
import Util.SimpleMapper;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.create_chat_session;
import group_6_model_sequential.machine3;

public class CreateChatSessionWrapper extends create_chat_session {
    protected MachineWrapper machineWrapper;
    private Activity activity;

    public CreateChatSessionWrapper(MachineWrapper m, Activity activity) {
        super(m);
        machineWrapper = m;
        this.activity = activity;
    }

    @Override
    public void run_create_chat_session(Integer ccs_u1, Integer ccs_u2) {
        if (super.guard_create_chat_session(ccs_u1, ccs_u2)) {
            super.run_create_chat_session(ccs_u1, ccs_u2);
            Settings.getInstance().setMyMachine(machineWrapper);
            SimpleMapper.toDatabaseReference(machineWrapper,
                    Settings.getInstance().getCurrentUser().getId());
            MessagingActivity.startActivity(activity, ccs_u2);
        }
    }
}