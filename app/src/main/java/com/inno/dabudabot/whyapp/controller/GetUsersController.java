package com.inno.dabudabot.whyapp.controller;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GetUsersView;
import com.inno.dabudabot.whyapp.models.MachineWrapper;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import WhyApp_Model_Group_6_sequential.machine3;
import eventb_prelude.BSet;

/**
 * Created by Group-6 on 07.11.17.
 */

public class GetUsersController {

    private GetUsersView listener;
    private machine3 mac;

    public GetUsersController(GetUsersView listener) {
        this.listener = listener;
        this.mac = MachineWrapper.getInstance();
    }

    public void getUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots =
                                dataSnapshot.getChildren().iterator();
                        List<User> users = new ArrayList<>();
                        BSet<Integer> userIds = new BSet<>();
                        while (dataSnapshots.hasNext()) {
                            DataSnapshot dataSnapshotChild =
                                    dataSnapshots.next();
                            User user =
                                    dataSnapshotChild.getValue(User.class);

                            if (!TextUtils.equals(
                                    user.getUid(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid())) {
                                users.add(user);
                                userIds.add(user.getId());
                            }
                        }
                        mac.set_user(userIds);
                        listener.onGetUsersSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onGetUsersFailure(databaseError.getMessage());
                    }
                });
    }

}
