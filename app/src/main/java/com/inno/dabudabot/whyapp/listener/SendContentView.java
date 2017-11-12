package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.sync.SendContentController;

import group_6_model_sequential.Content;

/**
 * Created by Group-6 on 10.11.17.
 * listener for {@link SendContentController}
 */

public interface SendContentView {

    void sendSuccess(Content content);

    void sendFailure(String message);

}
