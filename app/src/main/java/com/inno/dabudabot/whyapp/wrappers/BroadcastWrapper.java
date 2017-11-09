package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.broadcast;
import group_6_model_sequential.machine3;

public class BroadcastWrapper extends broadcast {
    protected MachineWrapper machineWrapper;
    
    public BroadcastWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }
}
