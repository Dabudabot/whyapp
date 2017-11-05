package com.inno.dabudabot.whyapp.core.chat;

import android.content.Context;

import com.inno.dabudabot.whyapp.models.Message;

/**
 * Created by Daulet on 10/21/17.
 */

public class ChatPresenter implements ChatContract.Presenter, ChatContract.OnSendMessageListener,
        ChatContract.OnGetMessagesListener {
    private ChatContract.View mView;
    private ChatInteractor mChatInteractor;

    public ChatPresenter(ChatContract.View view) {
        this.mView = view;
        mChatInteractor = new ChatInteractor(this, this);
    }

    @Override
    public void sendMessage(Context context, Message message, String receiverFirebaseToken) {
        mChatInteractor.sendMessageToFirebaseUser(context, message, receiverFirebaseToken);
    }

    @Override
    public void getMessage(String senderUid, String receiverUid) {
        mChatInteractor.getMessageFromFirebaseUser(senderUid, receiverUid);
    }

    @Override
    public void onSendMessageSuccess() {
        mView.onSendMessageSuccess();
    }

    @Override
    public void onSendMessageFailure(String message) {
        mView.onSendMessageFailure(message);
    }

    @Override
    public void onGetMessagesSuccess(Message message) {
        mView.onGetMessagesSuccess(message);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        mView.onGetMessagesFailure(message);
    }
}
