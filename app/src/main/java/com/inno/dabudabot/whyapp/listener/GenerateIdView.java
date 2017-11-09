package com.inno.dabudabot.whyapp.listener;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.inno.dabudabot.whyapp.controller.auth.GenerateIdController;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link GenerateIdController}
 */

public interface GenerateIdView {

    void onGenerateSuccess(Activity activity, Object target, Integer id);

    void onGenerateFailure(String message);

}
