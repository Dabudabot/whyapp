package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.sync.ReceiveContentController;

/**
 * Created by Group-6 on 11.11.17.
 * listener for {@link ReceiveContentController}
 */
public interface ReceiveContentView {

    void onReceiveSuccess();

    void onReceiveFailure(String message);

    Integer getSender();

    Integer getReceiver();

}
