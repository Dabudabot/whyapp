package com.inno.dabudabot.whyapp.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.inno.dabudabot.whyapp.R;
import com.inno.dabudabot.whyapp.controller.sync.ReceiveContentController;
import com.inno.dabudabot.whyapp.controller.sync.SendContentController;
import com.inno.dabudabot.whyapp.controller.listing.GetListingsController;
import com.inno.dabudabot.whyapp.listener.GetListingsView;
import com.inno.dabudabot.whyapp.listener.SendContentView;
import com.inno.dabudabot.whyapp.wrappers.DeleteContentWrapper;
import com.inno.dabudabot.whyapp.wrappers.ForwardWrapper;
import com.inno.dabudabot.whyapp.wrappers.RemoveContentWrapper;

import java.util.List;

import Util.Constants;
import Util.Settings;
import eventb_prelude.BRelation;
import eventb_prelude.BSet;
import eventb_prelude.Pair;
import group_6_model_sequential.Content;
import group_6_model_sequential.User;

/**
 * Created by dabudabot on 12.11.17.
 */

public class MessagingDialog extends Dialog implements
        android.view.View.OnClickListener, GetListingsView, SendContentView {

    private Activity c;
    private Integer contentId;
    private Integer userId;
    private Integer i;
    private GetListingsController getListingsController;
    private SendContentController sendContentController;
    private BSet<Integer> userIds;

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
        getListingsController = new GetListingsController(this);
        sendContentController = new SendContentController(this);
        userIds = new BSet<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forward:
                Settings.getInstance().setForwardingContent(contentId);
                getListingsController.getChatsListing();
                break;
            case R.id.delete_msg:
                if (Settings.getInstance()
                        .getCurrentUser().getId().equals(this.userId)) {
                    Integer snd = -1;
                    for (Pair<Integer, Integer> pair :
                            Settings.getInstance().getMachine().get_active()) {
                        snd = pair.snd();
                    }
                    new RemoveContentWrapper()
                            .runRemoveContent(contentId,
                                    Settings.getInstance().getCurrentUser().getId(),
                                    snd,
                                    i,
                                    Settings.getInstance().getMachine());
                    ReceiveContentController.sendNotify(
                            Settings.getInstance().getCurrentUser().getId(),
                            snd);
                } else {
                    new DeleteContentWrapper()
                            .runDeleteContent(contentId,
                                    Settings.getInstance().getCurrentUser().getId(),
                                    userId,
                                    i,
                                    Settings.getInstance().getMachine());
                    ReceiveContentController.sendNotify(
                            Settings.getInstance().getCurrentUser().getId(),
                            userId);
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onGetListingsSuccess(List<User> users) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final List<User> userList = users;
        String[] names = new String[users.size()];
        final boolean[] checkedNames = new boolean[users.size()];
        int i = 0;
        for (User user : userList) {
            names[i] = user.getName();
            checkedNames[i] = false;
            i++;
        }

        builder.setMultiChoiceItems(names, checkedNames,
                new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedNames[which] = isChecked;
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("User chooser");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                Integer forwardingContent = Settings.getInstance().getForwardingContent();
                Settings.getInstance().setForwardingContent(null);

                for (int i = 0; i < checkedNames.length; i++) {
                    if (checkedNames[i]) {
                        userIds.add(userList.get(i).getId());
                    }
                }

                sendContentController.send(
                        Settings.getInstance()
                                .getContents()
                                .get(forwardingContent)
                                .getMessage(),
                        getOwnerActivity());
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Settings.getInstance().setForwardingContent(null);
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    @Override
    public void onGetListingsFailure(String message) {

    }

    @Override
    public void sendSuccess(Content content) {
        new ForwardWrapper().runForward(
                content.getId(),
                Settings.getInstance().getCurrentUser().getId(),
                userIds,
                Settings.getInstance().getMachine());
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.NODE_CONTENTS)
                .child(content.getId().toString())
                .setValue(content);
    }

    @Override
    public void sendFailure(String message) {

    }
}