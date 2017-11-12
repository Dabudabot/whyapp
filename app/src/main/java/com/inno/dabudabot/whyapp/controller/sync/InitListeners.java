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

import eventb_prelude.BRelation;
import group_6_model_sequential.Content;
import group_6_model_sequential.machine3;
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
                                machine3 machine =
                                        SimpleMapper.toMachine(dataSnapshot.child(Constants.NODE_MACHINE));
                                Settings.getInstance().setMachine(machine);

                                initData();
                                initMuted();
                                initUser();
                                initContent();
                            }
                            Settings.getInstance().getUsers().put(user.getId(), user);
                        }
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_CONTENTS)
                                .getChildren()) {
                            Content content = child.getValue(Content.class);
                            Settings.getInstance().getContents().put(content.getId(), content);
                        }
                        ChatsListingActivity.onInitSuccess(context);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("BAD init");
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
                                machine3 machine =
                                        SimpleMapper.toMachine(dataSnapshot.child(Constants.NODE_MACHINE));
                                Settings.getInstance().setMachine(machine);

                                initData();
                                initMuted();
                                initUser();
                                initContent();
                            }
                            Settings.getInstance().getUsers().put(user.getId(), user);
                        }
                        for (DataSnapshot child : dataSnapshot.child(Constants.NODE_CONTENTS)
                                .getChildren()) {
                            Content content = child.getValue(Content.class);
                            Settings.getInstance().getContents().put(content.getId(), content);
                        }
                        ChatsListingActivity.onInitSuccess(context, flags);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("BAD init");
                    }
                });
    }

    private void initData() {
        ValueEventListener dataChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                machine3 machine = SimpleMapper.fromFirebaseString(
                        Settings.getInstance().getMachine(),
                        dataSnapshot.getValue(String.class));
                Settings.getInstance().uptDataMachine(machine);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("BAD init chatcontent");
            }
        };

        Settings.getInstance().setChatcontentChangeListener(dataChangeListener);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINE)
                .child(Constants.NODE_DATA)
                .addValueEventListener(dataChangeListener);
    }

    private void initMuted() {
        ValueEventListener mutedChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BRelation<Integer, Integer> muted =
                        SimpleMapper.toBRelation(dataSnapshot);
                Settings.getInstance().uptMuted(muted);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("BAD init muted");
            }
        };

        Settings.getInstance().setMutedChangeListener(mutedChangeListener);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_MACHINE)
                .child(Constants.NODE_MUTED)
                .addValueEventListener(mutedChangeListener);
    }

    private void initUser() {
        ChildEventListener addUserListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Settings.getInstance().getUsers().put(user.getId(), user);
                Settings.getInstance().uptUser(user.getId());
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

        Settings.getInstance().setNewUserListener(addUserListener);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_USERS)
                .addChildEventListener(addUserListener);
    }

    private void initContent() {
        ChildEventListener addContentListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Content content = dataSnapshot.getValue(Content.class);
                Settings.getInstance().getContents().put(content.getId(), content);
                Settings.getInstance().uptContent(content.getId());
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

        Settings.getInstance().setAddContentListener(addContentListener);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_CONTENTS)
                .addChildEventListener(addContentListener);
    }
}
