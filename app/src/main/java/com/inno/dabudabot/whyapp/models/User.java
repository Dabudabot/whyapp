package com.inno.dabudabot.whyapp.models;

/**
 * Created by Daulet on 10/21/17.
 */
public class User {
    private String uid;
    private String email;
    private String firebaseToken;

    public User(){

    }

    public User(String uid, String email, String firebaseToken){
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        if (email != null && email.contains("@test.com")) {
            return email.substring(0, email.indexOf("@test.com"));
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
