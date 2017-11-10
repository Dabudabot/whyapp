package com.inno.dabudabot.whyapp.controller.sync;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    public void init() {

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

        ChildEventListener newContent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                Content content = dataSnapshot.getValue(Content.class);
                Settings.getInstance().getContents().put(id, content);
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

        ChildEventListener newMachine = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                if (!id.equals(Settings.getInstance().getCurrentId())) {
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

        Settings.getInstance().setNewUserListener(newUser);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_USERS).addChildEventListener(newUser);
        Settings.getInstance().setNewContentListener(newContent);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_CONTENTS).addChildEventListener(newContent);
        Settings.getInstance().setNewMachineListener(newMachine);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINES).addChildEventListener(newMachine);

        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINES)
                .child(String.valueOf(Settings.getInstance().getCurrentId()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Settings.getInstance().setMyMachine(
                                SimpleMapper.toMachine(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
