package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.auth.LoginController;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link LoginController}
 */
public interface LoginView {
    void onLoginSuccess(String message);

    void onLoginFailure(String message);
}
