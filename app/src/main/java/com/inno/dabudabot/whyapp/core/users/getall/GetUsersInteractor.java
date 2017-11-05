package com.inno.dabudabot.whyapp.core.users.getall;

import android.text.TextUtils;

import com.inno.dabudabot.whyapp.models.Message;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class GetUsersInteractor implements GetUsersContract.Interactor {
    private static final String TAG = "GetUsersInteractor";

    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;
    private GetUsersContract.OnGetChatUsersListener mOnGetChatUsersListener;

    public GetUsersInteractor(GetUsersContract.OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
    }

    public GetUsersInteractor(GetUsersContract.OnGetChatUsersListener onGetChatUsersListener) {
        this.mOnGetChatUsersListener = onGetChatUsersListener;
    }


    @Override
    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                List<User> users = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if (!TextUtils.equals(
                            user.getUid(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        users.add(user);
                    }
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getChatUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots =
                        dataSnapshot.child(Constants.ARG_CHAT_ROOMS).getChildren().iterator();
                List<User> users = new ArrayList<>();
                while (dataSnapshots.hasNext()){
                    DataSnapshot dataSnapshotChild=dataSnapshots.next();
                    String senderId =
                            dataSnapshotChild.getKey().substring(
                                    0,
                                    dataSnapshotChild.getKey().indexOf("_"));

                    if(TextUtils.equals(
                            senderId,
                            FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        String recieverId =
                                dataSnapshotChild.getKey().substring(
                                        dataSnapshotChild.getKey().indexOf("_") + 1);
                        User user = dataSnapshot
                                .child(Constants.ARG_USERS)
                                .child(recieverId)
                                .getValue(User.class);
                        users.add(user);
                    }
                }
                mOnGetChatUsersListener.onGetChatUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetChatUsersListener.onGetChatUsersFailure(databaseError.getMessage());
            }
        });
    }
}
