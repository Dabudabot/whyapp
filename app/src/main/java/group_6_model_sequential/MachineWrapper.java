package group_6_model_sequential;

import whyapp.extensions.*;

public class MachineWrapper extends machine3 {
	
	public MachineWrapper() {
		super();
		
		evt_create_chat_session = new CreateChatSessionWrapper(this);
		evt_broadcast = new BroadcastWrapper(this);
		evt_delete_chat_session = new DeleteChatSessionWrapper(this);
		evt_unmute_chat = new UnmuteChatWrapper(this);
		evt_select_chat = new SelectChatWrapper(this);
		evt_unselect_chat = new UnselectChatWrapper(this);
		evt_forward = new ForwardWrapper(this);
		evt_reading = new ReadingWrapper(this);
		evt_delete_content = new DeleteContentWrapper(this);
		evt_mute_chat = new MuteChatWrapper(this);
		evt_chatting = new ChattingWrapper(this);
		evt_remove_content = new RemoveContentWrapper(this);
	}
}
