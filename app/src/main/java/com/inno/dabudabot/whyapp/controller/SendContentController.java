package com.inno.dabudabot.whyapp.controller;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.controller.auth.GenerateIdController;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;
import com.inno.dabudabot.whyapp.listener.SendContentView;
import com.inno.dabudabot.whyapp.wrappers.ChattingWrapper;

import Util.Constants;
import Util.Settings;
import group_6_model_sequential.Content;

/**
 * Created by dabudabot on 10.11.17.
 */

public class SendContentController implements GenerateIdView {

    private SendContentView listener;
    private GenerateIdController generateIdController;
    private Integer senderId;
    private Integer receiverId;
    private ChattingWrapper chattingWrapper;

    public SendContentController(SendContentView listener) {
        this.listener = listener;
        generateIdController = new GenerateIdController(this);
        chattingWrapper = new ChattingWrapper(Settings.getInstance().getMergedMachine());
    }

    public void send(String message,
                     Integer senderId,
                     Integer receiverId,
                     Activity activity) {
        this.senderId = senderId;
        this.receiverId = receiverId;
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
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.NODE_CONTENTS)
                    .child(chatMessage.getId().toString())
                    .setValue(chatMessage)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                chattingWrapper.run_chatting(id, senderId, receiverId);
                                listener.sendSuccess();
                            } else {
                                listener.sendFailure("BAD");
                            }
                        }
                    });
        } else {
            onGenerateFailure("BAD TYPE");
        }
    }

    @Override
    public void onGenerateFailure(String message) {
        System.err.println("NOT TODAY");
    }
}