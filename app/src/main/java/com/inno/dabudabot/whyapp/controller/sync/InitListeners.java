package com.inno.dabudabot.whyapp.controller.sync;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.InitListenerView;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;
import Util.Constants;
import Util.Settings;
import Util.SimpleMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Group-6 on 07.11.17.
 */
//TODO: INIT MACHINE LISTENER
public class InitListeners {
/*
    private InitListenerView listener;
    private machine3 mac;

    public InitListeners(InitListenerView listener) {
        this.listener = listener;
        mac = MachineWrapper.getInstance();
    }

    public void init() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_ACTIVE).addValueEventListener(
                        new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mac.set_active(SimpleMapper.toBRelation(dataSnapshot));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onInitListenersFailure(databaseError.getMessage());
                }
        });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_CHAT).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_chat(SimpleMapper.toBRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_CHATCONTENT).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_chatcontent(SimpleMapper.toBRelationRelationRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_CHATCONTENTSEQ).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_chatcontentseq(SimpleMapper.toBRelationRelationRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_CONTENT).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_content(SimpleMapper.toBSet(dataSnapshot));
                        Iterator<DataSnapshot> ints = dataSnapshot.getChildren().iterator();
                        Map<Integer, Content> contents = new HashMap<>();
                        while (ints.hasNext()) {
                            DataSnapshot child = ints.next();
                            Integer i = child.getValue(Integer.class);
                            Content content = child.child(String.valueOf(i))
                                    .getValue(Content.class);
                            contents.put(i, content);
                        }
                        Settings.getInstance().setContents(contents);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_INACTIVE).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_inactive(SimpleMapper.toBRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_CONTENTSIZE).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_contentsize(dataSnapshot.getValue(Integer.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_MUTED).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_muted(SimpleMapper.toBRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_OWNER).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_owner(SimpleMapper.toBRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_TOREAD).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_toread(SimpleMapper.toBRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_TOREADCON).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_toreadcon(SimpleMapper.toBRelationRelation(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_USER).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mac.set_user(SimpleMapper.toBSet(dataSnapshot));
                        Iterator<DataSnapshot> ints = dataSnapshot.getChildren().iterator();
                        Map<Integer, User> users = new HashMap<>();
                        while (ints.hasNext()) {
                            DataSnapshot child = ints.next();
                            Integer i = child.getValue(Integer.class);
                            User user = child.child(String.valueOf(i))
                                    .getValue(User.class);
                            users.put(i, user);
                        }
                        Settings.getInstance().setUsers(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onInitListenersFailure(databaseError.getMessage());
                    }
                });
    }*/
}
