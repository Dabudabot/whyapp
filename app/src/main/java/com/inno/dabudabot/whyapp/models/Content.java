package com.inno.dabudabot.whyapp.models;

import android.text.format.DateFormat;

/**
 * Created by Group-6 on 06.11.17.
 */
public class Content {

    private Integer id;
    private String message;
    private String ownerName;
    private String ownerUid;
    private long timestamp;

    public Content(Integer id,
                   String message,
                   String ownerName,
                   String ownerUid,
                   long timestamp) {
        this.id = id;
        this.message = message;
        this.ownerName = ownerName;
        this.ownerUid = ownerUid;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return DateFormat.format(
                "dd-MM-yyyy (HH:mm:ss)",
                timestamp).toString();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }
}
