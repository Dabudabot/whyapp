package com.inno.dabudabot.whyapp.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.SendContentController;
import com.inno.dabudabot.whyapp.listener.SendContentView;
import com.inno.dabudabot.whyapp.ui.activities.UserChooserActivity;

import Util.Settings;

/**
 * Created by dabudabot on 12.11.17.
 */

public class BroadcastDialog extends Dialog implements
        android.view.View.OnClickListener, SendContentView {

    private Activity c;
    private Dialog d;
    private SendContentController sendContentController;
    private EditText broadcastMessage;

    public BroadcastDialog(Activity a) {
        super(a);
        this.c = a;
        sendContentController = new SendContentController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_broadcast);
        broadcastMessage = (EditText) findViewById(R.id.broadcast_message);
        Button runBroadcast = (Button) findViewById(R.id.run_broadcast);
        runBroadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.run_broadcast:
                sendContentController.send(
                        broadcastMessage.getText().toString(),
                        getOwnerActivity());
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void sendSuccess(Integer id) {
        broadcastMessage.setText("");
        Settings.getInstance().setBroadcastingContent(id);
        UserChooserActivity.startActivity(getContext());
        dismiss();
    }

    @Override
    public void sendFailure(String message) {
        System.err.println("BAD broadcast");
    }
}
