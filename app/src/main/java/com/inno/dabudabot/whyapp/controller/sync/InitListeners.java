package com.inno.dabudabot.whyapp.controller.sync;

import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.controller.ReceiveContentController;
import com.inno.dabudabot.whyapp.ui.activities.ChatsListingActivity;

import group_6_model_sequential.Content;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.User;
import Util.Constants;
import Util.Settings;
import Util.SimpleMapper;


/**
 * Created by Group-6 on 07.11.17.
 * Listeners initialization
 */
public class InitListeners {

    public void init(final Context context) {
        FirebaseDatabase.getInstance()
                .getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_USERS)
                                .getChildren()) {
                            User user = child.getValue(User.class);
                            if (TextUtils.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    user.getUid())) {
                                Settings.getInstance().setCurrentUser(user);
                                Settings.getInstance().setMyMachine(
                                        SimpleMapper.toMachine(
                                                dataSnapshot.child(Constants.NODE_MACHINES)
                                                        .child(String.valueOf(user.getUid()))));

                                initUser();
                                initContent();
                                initMachines();
                            }
                            Settings.getInstance().getUsers().put(user.getId(), user);
                        }
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_MACHINES)
                                .getChildren()) {
                            MachineWrapper machineWrapper = SimpleMapper.toMachine(child);
                            Settings.getInstance().putMachines(Integer.parseInt(child.getKey()), machineWrapper);
                        }
                        ChatsListingActivity.onInitSuccess(context);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.err.println("BAD-BAD-BAD");
                    }
                });
    }

    public void init(final Context context, final int flags) {
        FirebaseDatabase.getInstance()
                .getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_USERS)
                                .getChildren()) {
                            User user = child.getValue(User.class);
                            if (TextUtils.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    user.getUid())) {
                                Settings.getInstance().setCurrentUser(user);
                                Settings.getInstance().setMyMachine(
                                        SimpleMapper.toMachine(
                                                dataSnapshot.child(Constants.NODE_MACHINES)
                                                        .child(String.valueOf(user.getUid()))));

                                initUser();
                                initContent();
                                initMachines();
                            }
                            Settings.getInstance().getUsers().put(user.getId(), user);
                        }
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_MACHINES)
                                .getChildren()) {
                            MachineWrapper machineWrapper = SimpleMapper.toMachine(child);
                            Settings.getInstance().putMachines(Integer.parseInt(child.getKey()), machineWrapper);
                        }
                        ChatsListingActivity.onInitSuccess(context, flags);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("BAD_BAD");
                    }
                });
    }

    private void initUser() {
        ChildEventListener newUser = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                Settings.getInstance().getUsers().put(id, user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Settings.getInstance().setNewUserListener(newUser);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_USERS).addChildEventListener(newUser);
    }

    private void initMachines() {
        ChildEventListener newMachine = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                if (!id.equals(Settings.getInstance().getCurrentUser().getId())) {
                    MachineWrapper machineWrapper =
                            SimpleMapper.toMachine(dataSnapshot);
                    Settings.getInstance().putMachines(id, machineWrapper);

                    ValueEventListener machineChangeListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Integer id = Integer.parseInt(dataSnapshot.getKey());
                            MachineWrapper machineWrapper =
                                    SimpleMapper.toMachine(dataSnapshot);
                            Settings.getInstance().putMachines(id, machineWrapper);
                            ReceiveContentController.sendNotify(Settings.getInstance().getCurrentUser().getId(), id);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };

                    Settings.getInstance().getMachineChangeListeners().put(id, machineChangeListener);
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.NODE_MACHINES)
                            .child(String.valueOf(id))
                            .addValueEventListener(machineChangeListener);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Settings.getInstance().setNewMachineListener(newMachine);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINES).addChildEventListener(newMachine);
    }

    //move it
    private void initContent() {
        ChildEventListener newContent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                Content content = dataSnapshot.getValue(Content.class);
                Settings.getInstance().putContents(id, content);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Settings.getInstance().setNewContentListener(newContent);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_CONTENTS).addChildEventListener(newContent);
    }
}
