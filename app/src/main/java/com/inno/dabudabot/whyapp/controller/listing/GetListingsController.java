package com.inno.dabudabot.whyapp.controller.listing;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.inno.dabudabot.whyapp.listener.GetListingsView;

import Util.Settings;
import eventb_prelude.Pair;
import group_6_model_sequential.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        for (Pair<Integer, Integer> chat :
                Settings.getInstance().getMachine().get_chat()) {
            if (chat.fst().equals(Settings.getInstance().getCurrentUser().getId())) {
                for (User user : Settings.getInstance().getUsers().values()) {
                    if (user.getId().equals(chat.snd())) {
                        users.add(user);
                    }
                }
            }
        }

        listener.onGetListingsSuccess(new ArrayList<>(users));
    }

}
