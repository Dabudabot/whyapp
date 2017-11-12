package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.auth.RegisterController;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link RegisterController}
 */
public interface RegisterView {

    void onRegistrationSuccess(String message);

    void onRegistrationFailure(String message);
}
