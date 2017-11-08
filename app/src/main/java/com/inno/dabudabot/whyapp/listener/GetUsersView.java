package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.models.User;

import java.util.List;

/**
 * Created by dabudabot on 07.11.17.
 */

public interface GetUsersView {

    void onGetUsersSuccess(List<User> users);

    void onGetUsersFailure(String message);

}
