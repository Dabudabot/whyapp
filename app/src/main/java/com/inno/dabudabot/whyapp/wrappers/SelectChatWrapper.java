package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.select_chat;

public class SelectChatWrapper extends select_chat {
    protected MachineWrapper machineWrapper;
    
	public SelectChatWrapper(MachineWrapper m) {
		super(m);
		machineWrapper = m;
	}
}
