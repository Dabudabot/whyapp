package com.inno.dabudabot.whyapp.controller;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;

import java.util.Date;

/**
 * Created by dabudabot on 07.11.17.
 */

public class GenerateIdController {

    private GenerateIdView listener;

    public GenerateIdController(GenerateIdView listener) {
        this.listener = listener;
    }

    public void checkInDatabase(final Activity activity,
                                final FirebaseUser firebaseUser,
                                String node) {
        DatabaseReference database =
                FirebaseDatabase.getInstance()
                        .getReference().child(node);

        final Integer tempId = (int) new Date().getTime();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int salt = 0;
                while (dataSnapshot.hasChild(tempId.toString() + salt)) {
                    salt++;
                }
                listener.onGenerateSuccess(activity, firebaseUser,
                        Integer.parseInt(tempId.toString() + salt));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGenerateFailure(databaseError.getMessage());
            }
        });
    }
}
