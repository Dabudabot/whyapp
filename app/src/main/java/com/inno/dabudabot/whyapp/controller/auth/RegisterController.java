package com.inno.dabudabot.whyapp.controller.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inno.dabudabot.whyapp.listener.AddUserView;
import com.inno.dabudabot.whyapp.listener.GenerateIdView;
import com.inno.dabudabot.whyapp.listener.RegisterView;
import Util.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by Group-6 on 07.11.17.
 * controller registers new user
 */
public class RegisterController implements GenerateIdView, AddUserView {

    private RegisterView listener;
    private GenerateIdController generateIdController;
    private AddUserController addUserController;

    public RegisterController(RegisterView listener) {
        this.listener = listener;
        generateIdController = new GenerateIdController(this);
        addUserController = new AddUserController(this);
    }

    public void performFirebaseRegistration(final Activity activity,
                                            final String name,
                                            String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(name, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "performFirebaseRegistration:onComplete:"
                                + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            if (task.getException() != null) {
                                listener.onRegistrationFailure(
                                        task.getException().getMessage());
                            } else {
                                listener.onRegistrationFailure("Reg - BAD");
                            }
                        } else {
                            generateIdController.checkInDatabase(
                                    activity,
                                    task.getResult().getUser(),
                                    Constants.NODE_USERS);
                        }
                    }
                });
    }

    @Override
    public void onGenerateSuccess(Activity activity,
                                  Object target,
                                  Integer id) {
        if (target instanceof FirebaseUser) {
            addUserController.addUserToDatabase(
                    activity.getApplicationContext(),
                    (FirebaseUser) target,
                    id);
        } else {
            onGenerateFailure("BAD TYPE");
        }
    }

    @Override
    public void onGenerateFailure(String message) {
        listener.onRegistrationFailure(message);
    }

    @Override
    public void onAddUserSuccess(String message) {
        listener.onRegistrationSuccess(message);
    }

    @Override
    public void onAddUserFailure(String message) {
        listener.onRegistrationFailure(message);
    }
}
