package com.inno.dabudabot.whyapp.listener;

import com.inno.dabudabot.whyapp.controller.sync.InitListeners;

/**
 * Created by Group-6 on 07.11.17.
 * listener for {@link InitListeners}
 */

public interface InitListenerView {

    void onInitListenersSuccess();

    void onInitListenersFailure(String message);

}
