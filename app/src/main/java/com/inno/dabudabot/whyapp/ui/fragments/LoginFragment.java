package com.inno.dabudabot.whyapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.auth.LoginController;
import com.inno.dabudabot.whyapp.controller.sync.InitListeners;
import com.inno.dabudabot.whyapp.listener.LoginView;
import com.inno.dabudabot.whyapp.controller.auth.RegisterController;
import com.inno.dabudabot.whyapp.listener.RegisterView;
import com.inno.dabudabot.whyapp.ui.activities.ChatsListingActivity;

/**
 * Created by Group-6 on 10/21/17.
 * Main content of login\register page
 *
 * User tries to login,
 * if user do not exist or device is wrong
 * registration starts and new user with this data created
 */
public class LoginFragment
        extends Fragment
        implements View.OnClickListener,
        LoginView,
        RegisterView {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginController loginController;
    private RegisterController registerController;
    private InitListeners initListeners;

    private EditText mETxtUsername;
    private Button mBtnLogin;

    private ProgressDialog mProgressDialog;

    private String username;
    private String pass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(
                        R.layout.fragment_login,
                        container,
                        false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mETxtUsername = (EditText) view.findViewById(R.id.edit_text_username_id);
        mBtnLogin = (Button) view.findViewById(R.id.button_login);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        initListeners = new InitListeners();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_login:
                onLogin();
                break;
        }
    }

    private void onLogin() {
        pass = Settings.Secure.getString(
                getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        username = mETxtUsername.getText().toString() + "@test.com";

        loginController.performFirebaseLogin(getActivity(), username, pass);
        mProgressDialog.show();
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(),
                "Logged in successfully",
                Toast.LENGTH_SHORT).show();
        initListeners.init();
        ChatsListingActivity.startActivity(getActivity(),
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getActivity(),
                "Registration: " + message,
                Toast.LENGTH_SHORT).show();
        registerController.performFirebaseRegistration(getActivity(), username, pass);
    }

    @Override
    public void onRegistrationSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        initListeners.init();
        ChatsListingActivity.startActivity(getActivity(),
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.please_wait));
        Log.e(TAG, "onRegistrationFailure: " + message);
        Toast.makeText(getActivity(),
                "Registration failed!+\n" + message, Toast.LENGTH_LONG).show();
    }
}
