package com.inno.dabudabot.whyapp.listener;

/**
 * Created by Group-6 on 07.11.17.
 */

public interface LoginView {
    void onLoginSuccess(String message);

    void onLoginFailure(String message);
}
