package com.inno.dabudabot.whyapp.controller.listing;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GetListingsView;

import Util.Settings;
import eventb_prelude.BRelation;
import eventb_prelude.Pair;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.User;
import Util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import eventb_prelude.BSet;

/**
 * Created by Group-6 on 07.11.17.
 * Controller return lists of users
 */

public class GetListingsController {

    private GetListingsView listener;

    public GetListingsController(GetListingsView listener) {
        this.listener = listener;
    }

    public void getUsersListing() {
        List<User> users = new ArrayList<>();
        for (User user : Settings.getInstance().getUsers().values()) {
            if (!TextUtils.equals(user.getUid(),
                    FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                users.add(user);
            }
        }
        listener.onGetListingsSuccess(users);
    }

    public void getChatsListing() {
        Set<User> users = new HashSet<>();

        for (MachineWrapper machineWrapper :
                Settings.getInstance().getMachines().values()) {
            for (Pair<Integer, Integer> chat : machineWrapper.get_chat()) {
                if (chat.fst().equals(Settings.getInstance().getCurrentUser().getId())) {
                    for (User user : Settings.getInstance().getUsers().values()) {
                        if (user.getId().equals(chat.snd())) {
                            users.add(user);
                        }
                    }
                }
                if (chat.snd().equals(Settings.getInstance().getCurrentUser().getId())) {
                    for (User user : Settings.getInstance().getUsers().values()) {
                        if (user.getId().equals(chat.fst())) {
                            users.add(user);
                        }
                    }
                }
            }
        }

        for (Pair<Integer, Integer> chat : Settings.getInstance().getMyMachine().get_chat()) {
            if (chat.fst().equals(Settings.getInstance().getCurrentUser().getId())) {
                for (User user : Settings.getInstance().getUsers().values()) {
                    if (user.getId().equals(chat.snd())) {
                        users.add(user);
                    }
                }
            }
            if (chat.snd().equals(Settings.getInstance().getCurrentUser().getId())) {
                for (User user : Settings.getInstance().getUsers().values()) {
                    if (user.getId().equals(chat.fst())) {
                        users.add(user);
                    }
                }
            }
        }

        listener.onGetListingsSuccess(new ArrayList<>(users));
    }

}
