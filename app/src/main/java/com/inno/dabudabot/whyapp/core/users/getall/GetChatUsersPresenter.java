package com.inno.dabudabot.whyapp.core.users.getall;

import com.inno.dabudabot.whyapp.models.User;

import java.util.List;

/**
 * Created by dabudabot on 04.11.17.
 */

public class GetChatUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetChatUsersListener {
    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetChatUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }

    @Override
    public void getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase();
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {
        mView.onGetChatUsersSuccess(users);
    }

    @Override
    public void onGetChatUsersFailure(String message) {
        mView.onGetChatUsersFailure(message);
    }
}
