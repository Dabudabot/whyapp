package com.inno.dabudabot.whyapp.controller.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.LoginView;
import group_6_model_sequential.User;
import Util.Constants;
import Util.Settings;
import Util.SharedPrefUtil;

import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by Group-6 on 07.11.17.
 * controller logs in user
 */

public class LoginController {

    private LoginView listener;

    public LoginController(LoginView listener) {
        this.listener = listener;
    }

    public void performFirebaseLogin(final Activity activity,
                                     final String email,
                                     String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "performFirebaseLogin:onComplete:"
                                + task.isSuccessful());

                        if (task.isSuccessful()) {
                            listener.onLoginSuccess(task.getResult().toString());
                            updateFirebaseToken(task.getResult().getUser().getUid(),
                                    new SharedPrefUtil(activity.getApplicationContext())
                                            .getString(
                                                    Constants.NODE_FIREBASE_TOKEN,
                                                    null));
                        } else {
                            if (task.getException() != null) {
                                listener.onLoginFailure(task.getException().getMessage());
                            } else {
                                listener.onLoginFailure("BAD");
                            }
                        }
                    }
                });
    }

    private void updateFirebaseToken(final String uid, final String token) {
        DatabaseReference database =
                FirebaseDatabase.getInstance()
                        .getReference().child(Constants.NODE_USERS);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots =
                        dataSnapshot.getChildren().iterator();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if (TextUtils.equals(
                            user.getUid(),
                            uid)) {
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(Constants.NODE_USERS)
                                .child(user.getId().toString())
                                .child(Constants.NODE_FIREBASE_TOKEN)
                                .setValue(token);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "tokeUpdateFailed: " + databaseError.getMessage());
            }
        });
    }
}
