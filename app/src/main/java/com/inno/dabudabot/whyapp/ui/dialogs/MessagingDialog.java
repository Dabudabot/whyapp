package com.inno.dabudabot.whyapp.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.ui.activities.UserChooserActivity;
import com.inno.dabudabot.whyapp.wrappers.DeleteContentWrapper;
import com.inno.dabudabot.whyapp.wrappers.ForwardWrapper;
import com.inno.dabudabot.whyapp.wrappers.RemoveContentWrapper;

import Util.Settings;
import eventb_prelude.BRelation;
import eventb_prelude.Pair;

/**
 * Created by dabudabot on 12.11.17.
 */

public class MessagingDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Integer contentId;
    private Integer userId;
    private Integer i;

    public MessagingDialog(Activity a, Integer contentId) {
        super(a);
        this.c = a;
        this.contentId = contentId;

        for (Pair<Integer, Integer> pair :
                Settings.getInstance().getMachine().get_owner()) {
            if (pair.fst().equals(contentId)) {
                this.userId = pair.snd();
            }
        }

        for (Pair<Integer, BRelation<Integer, BRelation<Integer, Integer>>> pair :
                Settings.getInstance().getMachine().get_chatcontentseq()) {
            for (Pair<Integer, BRelation<Integer, Integer>> pair1 :
                    pair.snd()) {
                if (this.contentId.equals(pair1.fst())) {
                    this.i = pair.fst();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_messaging_i);
        Button forward = (Button) findViewById(R.id.forward);
        Button delete = (Button) findViewById(R.id.delete_msg);
        forward.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forward:
                Settings.getInstance().setForwardingContent(contentId);
                UserChooserActivity.startActivity(getContext());
                break;
            case R.id.delete_msg:
                if (Settings.getInstance()
                        .getCurrentUser().getId().equals(this.userId)) {
                    new RemoveContentWrapper()
                            .runRemoveContent(contentId,
                                    Settings.getInstance().getCurrentUser().getId(),
                                    userId,
                                    i,
                                    Settings.getInstance().getMachine());
                } else {
                    new DeleteContentWrapper()
                            .runDeleteContent(contentId,
                                    Settings.getInstance().getCurrentUser().getId(),
                                    userId,
                                    i,
                                    Settings.getInstance().getMachine());
                }

                break;
            default:
                break;
        }
        dismiss();
    }
}