package com.inno.dabudabot.whyapp.listener;

import group_6_model_sequential.Content;

/**
 * Created by dabudabot on 10.11.17.
 */

public interface SendContentView {

    void sendSuccess(Content content);

    void sendFailure(String message);

}
