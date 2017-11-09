package com.inno.dabudabot.whyapp.controller.listing;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GetListingsView;
import group_6_model_sequential.User;
import Util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                        listener.onGetListingsSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onGetListingsFailure(databaseError.getMessage());
                    }
                });
    }

    public void getChatsFromFirebase() {
        //TODO: NEED MODEL INTERACTION
    }

}
