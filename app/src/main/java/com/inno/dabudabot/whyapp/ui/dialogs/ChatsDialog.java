package com.inno.dabudabot.whyapp.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.wrappers.DeleteChatSessionWrapper;
import com.inno.dabudabot.whyapp.wrappers.MuteChatWrapper;

import Util.Settings;

/**
 * Created by Group-6 on 12.11.17.
 * Upper menu dialog in chat activity
 */
public class ChatsDialog  extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Integer userId;

    public ChatsDialog(Activity a, Integer userId) {
        super(a);
        this.c = a;
        this.userId = userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_chat);
        Button mute = (Button) findViewById(R.id.muteunmute);
        Button delete = (Button) findViewById(R.id.delete_chat);
        mute.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.muteunmute:
                new MuteChatWrapper().runMuteChat(
                        Settings.getInstance().getCurrentUser().getId(),
                        userId,
                        Settings.getInstance().getMachine());
                break;
            case R.id.delete_chat:
                new DeleteChatSessionWrapper().runDeleteChatSession(
                        Settings.getInstance().getCurrentUser().getId(),
                        userId,
                        Settings.getInstance().getMachine());
                break;
            default:
                break;
        }
        dismiss();
    }
}
