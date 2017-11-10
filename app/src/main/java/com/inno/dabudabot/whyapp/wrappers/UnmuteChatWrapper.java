package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.unmute_chat;

public class UnmuteChatWrapper extends unmute_chat {
    protected MachineWrapper machineWrapper;
    
	public UnmuteChatWrapper(MachineWrapper m) {
		super(m);
		machineWrapper = m;
	}
}
