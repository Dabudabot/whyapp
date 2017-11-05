package com.inno.dabudabot.whyapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.core.login.LoginContract;
import com.inno.dabudabot.whyapp.core.login.LoginPresenter;
import com.inno.dabudabot.whyapp.core.registration.RegisterContract;
import com.inno.dabudabot.whyapp.core.registration.RegisterPresenter;
import com.inno.dabudabot.whyapp.core.users.add.AddUserContract;
import com.inno.dabudabot.whyapp.core.users.add.AddUserPresenter;
import com.inno.dabudabot.whyapp.ui.activities.ChatUsersListingActivity;
import com.inno.dabudabot.whyapp.ui.activities.UserListingActivity;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daulet on 10/21/17.
 * Main content of login\register page
 */
public class LoginFragment
        extends Fragment
        implements View.OnClickListener,
        LoginContract.View,
        RegisterContract.View,
        AddUserContract.View {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginPresenter mLoginPresenter;
    private RegisterPresenter mRegisterPresenter;
    private AddUserPresenter mAddUserPresenter;

    private EditText mETxtUsername;
    private Button mBtnLogin;

    private ProgressDialog mProgressDialog;

    private String username;
    private String pass;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_login, container, false);
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
        mLoginPresenter = new LoginPresenter(this);
        mRegisterPresenter = new RegisterPresenter(this);
        mAddUserPresenter = new AddUserPresenter(this);

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
                onLogin(view);
                break;
        }
    }

    private void onLogin(View view) {
        pass = getMacAddress();
        username = mETxtUsername.getText().toString() + "@test.com";

        mLoginPresenter.login(getActivity(), username, pass);
        mProgressDialog.show();
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
        ChatUsersListingActivity.startActivity(getActivity(),
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getActivity(), "Registration: " + message, Toast.LENGTH_SHORT).show();
        mRegisterPresenter.register(getActivity(), username, pass);
    }

    /**
     * Get MAC address of devise
     * @return MAC address
     */
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all =
                    Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "SOME_EMPTY_PASSWORD";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF));
                    res1.append(":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            return "SOME_WRONG_PASSWORD";
        }
        return "SOME_DEFAULT_PASSWORD";
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        mProgressDialog.setMessage(getString(R.string.adding_user_to_db));
        Toast.makeText(getActivity(), "Registration Successful!", Toast.LENGTH_SHORT).show();
        mAddUserPresenter.addUser(getActivity().getApplicationContext(), firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.please_wait));
        Log.e(TAG, "onRegistrationFailure: " + message);
        Toast.makeText(getActivity(),
                "Registration failed!+\n" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddUserSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        UserListingActivity.startActivity(getActivity(),
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onAddUserFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
