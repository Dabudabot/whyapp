package com.inno.dabudabot.whyapp.models;

/**
 * Created by Group-6 on 10/21/17.
 */
public class User {
    private String uid;
    private String name;
    private String firebaseToken;
    private Integer id;

    public User(){

    }

    public User(String uid, String name, String firebaseToken, Integer id){
        this.uid = uid;
        this.name = name;
        this.firebaseToken = firebaseToken;
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
