package com.inno.dabudabot.whyapp.controller.auth;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;
import Util.Settings;

/**
 * Created by Group-6 on 07.11.17.
 * controller generates unique id
 */

public class GenerateIdController {

    private GenerateIdView listener;

    public GenerateIdController(GenerateIdView listener) {
        this.listener = listener;
    }

    public void checkInDatabase(final Activity activity,
                                final Object target,
                                String node) {
        DatabaseReference database =
                FirebaseDatabase.getInstance()
                        .getReference().child(node);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id = Settings.getInstance().getIdGen();
                while (dataSnapshot.hasChild(String.valueOf(id))) {
                    id++;
                }
                Settings.getInstance().setIdGen(id);
                listener.onGenerateSuccess(activity, target, id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGenerateFailure(databaseError.getMessage());
            }
        });
    }
}
