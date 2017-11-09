package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.create_chat_session;
import group_6_model_sequential.machine3;

public class CreateChatSessionWrapper extends create_chat_session {
    protected MachineWrapper machineWrapper;

    public CreateChatSessionWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }
}    