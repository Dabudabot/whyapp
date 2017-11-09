package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.auth.AddUserController;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link AddUserController}
 */

public interface AddUserView {

    void onAddUserSuccess(String message);

    void onAddUserFailure(String message);
}
