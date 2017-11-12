package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import group_6_model_sequential.User;

import java.util.List;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link GetListingsController}
 */
public interface GetListingsView {

    void onGetListingsSuccess(List<User> users);

    void onGetListingsFailure(String message);

}
