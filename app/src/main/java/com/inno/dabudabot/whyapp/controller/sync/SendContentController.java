package com.inno.dabudabot.whyapp.controller.sync;

import android.app.Activity;

import com.inno.dabudabot.whyapp.controller.auth.GenerateIdController;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;
import com.inno.dabudabot.whyapp.listener.SendContentView;

import Util.Constants;
import group_6_model_sequential.Content;

/**
 * Created by Group-6 on 10.11.17.
 * Sends content to FireBase
 */
public class SendContentController implements GenerateIdView {

    private SendContentView listener;
    private GenerateIdController generateIdController;

    public SendContentController(SendContentView listener) {
        this.listener = listener;
        generateIdController = new GenerateIdController(this);
    }

    public void send(String message,
                     Activity activity) {
        generateIdController.checkInDatabase(
                activity,
                message,
                Constants.NODE_CONTENTS);
    }

    @Override
    public void onGenerateSuccess(Activity activity,
                                  Object target,
                                  final Integer id) {
        if (target instanceof String) {
            Content chatMessage = new Content(id,
                    (String) target,
                    System.currentTimeMillis());
            listener.sendSuccess(chatMessage);
        } else {
            onGenerateFailure("BAD TYPE");
        }
    }

    @Override
    public void onGenerateFailure(String message) {
        System.err.println("NOT TODAY");
    }
}
