package com.inno.dabudabot.whyapp.wrappers;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.reading;

public class ReadingWrapper extends reading {
    protected MachineWrapper machineWrapper;
    
    public ReadingWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }

}
