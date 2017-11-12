package com.inno.dabudabot.whyapp.listener;

import android.app.Activity;

import com.inno.dabudabot.whyapp.controller.sync.UpdateUsersController;

/**
 * Created by Group-6 on 08.11.17.
 * listener for {@link UpdateUsersController}
 */

public interface SyncView {

    void onSyncSuccess(Activity activity,
                       Integer userId);

    void onSyncFailure();

}
