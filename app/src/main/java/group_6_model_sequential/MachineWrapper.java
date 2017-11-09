package group_6_model_sequential;

import com.inno.dabudabot.whyapp.wrappers.BroadcastWrapper;
import com.inno.dabudabot.whyapp.wrappers.ChattingWrapper;
import com.inno.dabudabot.whyapp.wrappers.CreateChatSessionWrapper;
import com.inno.dabudabot.whyapp.wrappers.DeleteChatSessionWrapper;
import com.inno.dabudabot.whyapp.wrappers.DeleteContentWrapper;
import com.inno.dabudabot.whyapp.wrappers.ForwardWrapper;
import com.inno.dabudabot.whyapp.wrappers.MuteChatWrapper;
import com.inno.dabudabot.whyapp.wrappers.ReadingWrapper;
import com.inno.dabudabot.whyapp.wrappers.RemoveContentWrapper;
import com.inno.dabudabot.whyapp.wrappers.SelectChatWrapper;
import com.inno.dabudabot.whyapp.wrappers.UnmuteChatWrapper;
import com.inno.dabudabot.whyapp.wrappers.UnselectChatWrapper;

public class MachineWrapper extends machine3 {

	private static volatile MachineWrapper instance;

    public static MachineWrapper getInstance() {
        MachineWrapper localInstance = instance;
        if (localInstance == null) {
            synchronized (machine3.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MachineWrapper();
                }
            }
        }
        return localInstance;
    }

	private MachineWrapper() {
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
