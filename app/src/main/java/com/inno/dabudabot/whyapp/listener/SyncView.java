package com.inno.dabudabot.whyapp.listener;

import android.app.Activity;

import com.inno.dabudabot.whyapp.controller.sync.SyncController;

/**
 * Created by Group-6 on 08.11.17.
 * listener for {@link SyncController}
 */

public interface SyncView {

    void onSyncSuccess(Activity activity,
                       Integer userId);

    void onSyncFailure();

}
