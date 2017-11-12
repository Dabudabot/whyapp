package com.inno.dabudabot.whyapp.controller.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.activities.LoginActivity;

import java.util.HashMap;

import Util.Settings;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;

/**
 * Created by Group-6 on 07.11.17.
 * controller logs out the user
 */

public class LogoutController {

    public void performFirebaseLogout(final Activity activity) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            FirebaseAuth.getInstance().signOut();
            LoginActivity.startIntent(activity.getApplicationContext(),
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            Toast.makeText(
                    activity.getApplicationContext(),
                    "No user logged in yet!",
                    Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase.getInstance().getReference().removeEventListener(Settings.getInstance().getNewUserListener());
        FirebaseDatabase.getInstance().getReference().removeEventListener(Settings.getInstance().getAddContentListener());
        FirebaseDatabase.getInstance().getReference().removeEventListener(Settings.getInstance().getChatcontentChangeListener());
        FirebaseDatabase.getInstance().getReference().removeEventListener(Settings.getInstance().getMutedChangeListener());
        Settings.getInstance().setCurrentUser(null);
        Settings.getInstance().setContents(new HashMap<Integer, Content>());
        Settings.getInstance().setUsers(new HashMap<Integer, User>());
        Settings.getInstance().setMachine(null);
        Settings.getInstance().setIdGen(0);
        Settings.getInstance().setForwardingContent(null);
        Settings.getInstance().setBroadcastingContent(null);
    }
}
