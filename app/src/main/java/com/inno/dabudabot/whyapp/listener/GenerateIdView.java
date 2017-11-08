package com.inno.dabudabot.whyapp.listener;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dabudabot on 07.11.17.
 */

public interface GenerateIdView {

    void onGenerateSuccess(Activity activity, FirebaseUser firebaseUser, Integer id);

    void onGenerateFailure(String message);

}
