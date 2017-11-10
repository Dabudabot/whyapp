package com.inno.dabudabot.whyapp.controller.sync;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

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

    private void initNew() {
        ChildEventListener newContent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                Content content = dataSnapshot.getValue(Content.class);
                Settings.getInstance().getContents().put(id, content);
                //Settings.getInstance().getMyMachine().get_content().add(id);
                //Settings.getInstance().merge();
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

    private void initMyMachine() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINES)
                .child(String.valueOf(Settings.getInstance().getCurrentUser().getId()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Settings.getInstance().setMyMachine(
                                SimpleMapper.toMachine(dataSnapshot));
                        initUser();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void init() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_USERS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> childs = dataSnapshot.getChildren().iterator();
                        while(childs.hasNext()) {
                            DataSnapshot child = childs.next();
                            User user = child.getValue(User.class);
                            if (TextUtils.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    user.getUid())) {
                                Settings.getInstance().setCurrentUser(child.getValue(User.class));
                            }
                        }
                        initMyMachine();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void initUser() {
        ChildEventListener newUser = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                Settings.getInstance().getUsers().put(id, user);
                Settings.getInstance().getMyMachine().get_user().add(id);
                initNew();
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
}
