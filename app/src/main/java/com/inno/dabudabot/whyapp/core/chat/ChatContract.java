package com.inno.dabudabot.whyapp.core.chat;

import android.content.Context;

import com.inno.dabudabot.whyapp.models.Message;



public interface ChatContract {
    interface View {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessagesSuccess(Message message);

        void onGetMessagesFailure(String message);
    }

    interface Presenter {
        void sendMessage(Context context, Message message, String receiverFirebaseToken);

        void getMessage(String senderUid, String receiverUid);
    }

    interface Interactor {
        void sendMessageToFirebaseUser(Context context, Message message, String receiverFirebaseToken);

        void getMessageFromFirebaseUser(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);
    }

    interface OnGetMessagesListener {
        void onGetMessagesSuccess(Message message);

        void onGetMessagesFailure(String message);
    }
}
