package com.inno.dabudabot.whyapp.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.listener.AddUserView;
import com.inno.dabudabot.whyapp.models.User;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.Settings;
import com.inno.dabudabot.whyapp.utils.SharedPrefUtil;

/**
 * Created by Group-6 on 07.11.17.
 */

public class AddUserController {

    private AddUserView listener;

    public AddUserController(AddUserView listener) {
        this.listener = listener;
    }

    public void addUserToDatabase(final Context context,
                                  FirebaseUser firebaseUser,
                                  Integer id) {
        DatabaseReference database =
                FirebaseDatabase.getInstance()
                        .getReference().child(Constants.ARG_USER);

        User user = new User(firebaseUser.getUid(),
                firebaseUser.getEmail(),
                new SharedPrefUtil(context).getString(Constants.ARG_TOKEN),
                id);
        Settings.getInstance().setCurrentId(id);

        database.child(Constants.ARG_USER)
                .child(user.getId().toString())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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
