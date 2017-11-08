package com.inno.dabudabot.whyapp.controller;

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
import com.inno.dabudabot.whyapp.listener.InitListenerView;
import com.inno.dabudabot.whyapp.listener.LoginView;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.Settings;
import com.inno.dabudabot.whyapp.utils.SharedPrefUtil;

import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by Group-6 on 07.11.17.
 */

public class LoginController implements InitListenerView {

    private LoginView listener;
    private InitListeners initListeners;

    public LoginController(LoginView listener) {
        this.listener = listener;
        this.initListeners = new InitListeners(this);
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
                                                    Constants.ARG_TOKEN,
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
                        .getReference().child(Constants.ARG_USER);

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
                        Settings.getInstance().setCurrentId(user.getId());
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(Constants.ARG_USER)
                                .child(user.getId().toString())
                                .child(Constants.ARG_TOKEN)
                                .setValue(token);
                        initListeners.init();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "tokeUpdateFailed: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onInitListenersSuccess() {

    }

    @Override
    public void onInitListenersFailure(String message) {
        Log.e(TAG, "init failure: " + message);
    }
}
