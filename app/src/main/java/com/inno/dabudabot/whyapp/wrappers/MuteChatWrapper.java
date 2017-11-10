package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.mute_chat;

public class MuteChatWrapper extends mute_chat {
    protected MachineWrapper machineWrapper;
    
    public MuteChatWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }

}
