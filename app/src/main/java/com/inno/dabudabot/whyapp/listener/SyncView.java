package com.inno.dabudabot.whyapp.listener;

import android.app.Activity;


/**
 * Created by Group-6 on 08.11.17.
 */

public interface SyncView {

    void onSyncSuccess(Activity activity,
                       Integer userId);

    void onSyncFailure();

}
