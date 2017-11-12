package com.inno.dabudabot.whyapp.controller.sync;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GetChatContentView;
import com.inno.dabudabot.whyapp.ui.activities.ChatsListingActivity;

import Util.Constants;
import Util.Settings;
import Util.SimpleMapper;
import eventb_prelude.BRelation;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;
import group_6_model_sequential.machine3;

/**
 * Created by dabudabot on 12.11.17.
 */

public class GetChatContentController {

    private GetChatContentView listener;

    public GetChatContentController(GetChatContentView listener) {
        this.listener = listener;
    }

    public void get() {

    }
}
