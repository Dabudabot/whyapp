package com.inno.dabudabot.whyapp.listener;

/**
 * Created by dabudabot on 11.11.17.
 */

public interface ReceiveContentView {

    void onReceiveSuccess();

    void onReceiveFailure(String message);

    Integer getSender();

    Integer getReceiver();

}
