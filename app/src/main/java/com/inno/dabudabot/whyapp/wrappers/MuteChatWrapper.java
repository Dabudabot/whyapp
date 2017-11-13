package com.inno.dabudabot.whyapp.wrappers;

import Util.Settings;
import group_6_model_sequential.machine3;
import group_6_model_sequential.mute_chat;

public class MuteChatWrapper {

    public MuteChatWrapper() {}

    public boolean guardMuteChat(Integer mc_u1,
                              Integer mc_u2,
                              machine3 m) {
        return new mute_chat(m).guard_mute_chat(mc_u1, mc_u2);
    }

    public void runMuteChat(Integer mc_u1,
                            Integer mc_u2,
                            machine3 m) {
        mute_chat mute_chat = new mute_chat(m);
        if (mute_chat.guard_mute_chat(mc_u1, mc_u2)) {
            Settings.getInstance().setBusy(true);
            mute_chat.run_mute_chat(mc_u1, mc_u2);
            Settings.getInstance().commitMachine(m);
            Settings.getInstance().setBusy(false);
        }
    }
}
