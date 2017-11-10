package com.inno.dabudabot.whyapp.controller.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.activities.LoginActivity;

/**
 * Created by Group-6 on 07.11.17.
 * controller logs out the user
 */

public class LogoutController {

    public void performFirebaseLogout(final Activity activity) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity.getApplicationContext())
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            //TODO 9 clear settings
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
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(activity.getResources().getColor(R.color.grey_900));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(activity.getResources().getColor(R.color.grey_900));
    }
}
