package com.inno.dabudabot.whyapp.models;

import android.text.format.DateFormat;

/**
 * Created by Daulet on 10/21/17.
 */
public class Chat  {
    private String sender;
    private String receiver;
    private String senderUid;
    private String receiverUid;
    private String message;
    private long timestamp;

    public Chat(){
    }

    public Chat(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp){
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
    }


    public String getSender() {
        if (sender != null && sender.contains("@test.com")) {
            return sender.substring(0, sender.indexOf("@test.com"));
        }
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        if (receiver != null && receiver.contains("@test.com")) {
            return receiver.substring(0, receiver.indexOf("@test.com"));
        }
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTime() {
        return DateFormat.format("dd-MM-yyyy (HH:mm:ss)", timestamp).toString();
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
