package com.inno.dabudabot.whyapp.controller.auth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.listener.AddUserView;

import Util.SimpleMapper;
import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.User;
import Util.Constants;
import Util.Settings;
import Util.SharedPrefUtil;

/**
 * Created by Group-6 on 07.11.17.
 * controller adds new user into base
 */
public class AddUserController {

    private AddUserView listener;

    public AddUserController(AddUserView listener) {
        this.listener = listener;
    }

    public void addUserToDatabase(final Context context,
                                  FirebaseUser firebaseUser,
                                  final Integer id) {

        final User user = new User(firebaseUser.getUid(),
                firebaseUser.getEmail(),
                new SharedPrefUtil(context).getString(Constants.NODE_FIREBASE_TOKEN),//it returns null
                id);

        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_USERS)
                .child(user.getId().toString())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            MachineWrapper machineWrapper = new MachineWrapper();
                            SimpleMapper.toDatabaseReference(machineWrapper, id);
                            listener.onAddUserSuccess(
                                    context.getString(R.string.user_successfully_added));
                        } else {
                            listener.onAddUserFailure(
                                    context.getString(R.string.user_unable_to_add));
                        }
                    }
                });
    }
}
