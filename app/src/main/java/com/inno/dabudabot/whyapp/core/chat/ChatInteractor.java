package com.inno.dabudabot.whyapp.core.chat;

import android.content.Context;
import android.util.Log;

import com.inno.dabudabot.whyapp.fcm.FcmNotificationBuilder;
import com.inno.dabudabot.whyapp.utils.Constants;
import com.inno.dabudabot.whyapp.utils.SharedPrefUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Daulet on 10/21/17.
 */

public class ChatInteractor implements ChatContract.Interactor {
    private static final String TAG = "ChatInteractor";

    private ChatContract.OnSendMessageListener mOnSendMessageListener;
    private ChatContract.OnGetMessagesListener mOnGetMessagesListener;

    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener) {
        this.mOnSendMessageListener = onSendMessageListener;
    }

    public ChatInteractor(ChatContract.OnGetMessagesListener onGetMessagesListener) {
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener,
                          ChatContract.OnGetMessagesListener onGetMessagesListener) {
        this.mOnSendMessageListener = onSendMessageListener;
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    @Override
    public void sendMessageToFirebaseUser(final Context context, final Message message, final String receiverFirebaseToken) {
        final String room_type_1 = message.getSenderUid() + "_" + message.getReceiverUid();
        final String room_type_2 = message.getReceiverUid() + "_" + message.getSenderUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_1 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(message.getTimestamp())).setValue(message);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_2 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(message.getTimestamp())).setValue(message);
                } else {
                    Log.e(TAG, "sendMessageToFirebaseUser: success");//TODO: MOVE IT FROM HERE TO SEP
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(message.getTimestamp())).setValue(message);
                    getMessageFromFirebaseUser(message.getSenderUid(), message.getReceiverUid());
                }
                // send push notification to the receiver
                sendPushNotificationToReceiver(message.getSender(),
                        message.getMessage(),
                        message.getSenderUid(),
                        new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                        receiverFirebaseToken);
                mOnSendMessageListener.onSendMessageSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.getMessage());
            }
        });
    }

    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }

    @Override
    public void getMessageFromFirebaseUser(String senderUid, String receiverUid) {
        final String room_type_1 = senderUid + "_" + receiverUid;
        final String room_type_2 = receiverUid + "_" + senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    Log.e(TAG, "getMessageFromFirebaseUser: " + room_type_1 + " exists");
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Message message = dataSnapshot.getValue(Message.class);
                            mOnGetMessagesListener.onGetMessagesSuccess(message);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                        }
                    });
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "getMessageFromFirebaseUser: " + room_type_2 + " exists");
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Message message = dataSnapshot.getValue(Message.class);
                            mOnGetMessagesListener.onGetMessagesSuccess(message);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                        }
                    });
                } else {
                    Log.e(TAG, "getMessageFromFirebaseUser: no such room available");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
            }
        });
    }
}
