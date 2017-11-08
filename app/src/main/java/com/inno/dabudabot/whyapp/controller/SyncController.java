package com.inno.dabudabot.whyapp.controller;

import android.app.Activity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.listener.SyncView;
import com.inno.dabudabot.whyapp.models.MachineWrapper;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.Settings;

import WhyApp_Model_Group_6_sequential.machine3;
import eventb_prelude.BRelation;
import eventb_prelude.Pair;

/**
 * Created by dabudabot on 07.11.17.
 */

public class SyncController {
    //sync whole machine

    private SyncView listener;
    private machine3 mac;

    public SyncController(SyncView listener) {
        this.listener = listener;
        mac = MachineWrapper.getInstance();
    }

    public void sync(Activity activity,
                     Integer userId) {
        DatabaseReference database =
                FirebaseDatabase.getInstance().getReference();
        Task<Void> result = database.removeValue();
        if (result.isSuccessful()) {
            inflate(database, Constants.ARG_ACTIVE, mac.get_active());
            inflate(database, Constants.ARG_CHAT, mac.get_chat());
            inflateRelationRelation(database, Constants.ARG_CHATCONTENT, mac.get_chatcontent());
            inflateRelationRelation(database, Constants.ARG_CHATCONTENTSEQ, mac.get_chatcontentseq());
            for (Integer i : mac.get_content()) {
                database.child(Constants.ARG_CONTENT).child(String.valueOf(i))
                        .setValue(Settings.getInstance().getContents().get(i));
            }
            database.child(Constants.ARG_CONTENTSIZE).setValue(mac.get_contentsize());
            inflate(database, Constants.ARG_INACTIVE, mac.get_inactive());
            inflate(database, Constants.ARG_MUTED, mac.get_muted());
            inflate(database, Constants.ARG_OWNER, mac.get_owner());
            inflate(database, Constants.ARG_TOREAD, mac.get_toread());
            inflateRelation(database, Constants.ARG_TOREADCON, mac.get_toreadcon());
            for (Integer i : mac.get_user()) {
                database.child(Constants.ARG_USER).child(String.valueOf(i))
                        .setValue(Settings.getInstance().getUsers().get(i));
            }
            listener.onSyncSuccess(activity, userId);
        } else {
            listener.onSyncFailure();
        }
    }

    private void inflate(DatabaseReference database,
                         String node,
                         BRelation<Integer, Integer> value) {
        int i = 0;
        for (Pair<Integer, Integer> pair : value) {
            database.child(node).child(String.valueOf(i))
                    .child(Constants.ARG_FST).setValue(pair.fst());
            database.child(node).child(String.valueOf(i))
                    .child(Constants.ARG_SND).setValue(pair.snd());
            i++;
        }
    }

    private void inflateRelation(DatabaseReference database,
                         String node,
                         BRelation<Integer, BRelation<Integer, Integer>> value) {
        int i = 0;
        for (Pair<Integer, BRelation<Integer, Integer>> pair : value) {
            database.child(node).child(String.valueOf(i))
                    .child(Constants.ARG_FST).setValue(pair.fst());
            int j = 0;
            for (Pair<Integer, Integer> child : pair.snd()) {
                database.child(node).child(String.valueOf(i))
                        .child(Constants.ARG_SND).child(String.valueOf(j))
                        .child(Constants.ARG_FST).setValue(child.fst());
                database.child(node).child(String.valueOf(i))
                        .child(Constants.ARG_SND).child(String.valueOf(j))
                        .child(Constants.ARG_SND).setValue(child.snd());
                j++;
            }
            i++;
        }
    }

    private void inflateRelationRelation(DatabaseReference database,
                                 String node,
                                 BRelation<Integer, BRelation<Integer, BRelation<Integer, Integer>>> value) {
        int i = 0;
        for (Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> pair
                : mac.get_chatcontent()) {
            database.child(node).child(String.valueOf(i))
                    .child(Constants.ARG_FST).setValue(pair.fst());
            int j = 0;
            for (Pair<Integer, BRelation<Integer, Integer>> child : pair.snd()) {
                database.child(node).child(String.valueOf(i))
                        .child(Constants.ARG_SND).child(String.valueOf(j))
                        .child(Constants.ARG_FST).setValue(child.fst());
                int k = 0;
                for (Pair<Integer, Integer> gChild : child.snd()) {
                    database.child(node).child(String.valueOf(i))
                            .child(Constants.ARG_SND).child(String.valueOf(j))
                            .child(Constants.ARG_SND).child(String.valueOf(k))
                            .child(Constants.ARG_FST).setValue(gChild.fst());
                    database.child(node).child(String.valueOf(i))
                            .child(Constants.ARG_SND).child(String.valueOf(j))
                            .child(Constants.ARG_SND).child(String.valueOf(k))
                            .child(Constants.ARG_SND).setValue(gChild.snd());
                    k++;
                }
                j++;
            }
            i++;
        }
    }
}
