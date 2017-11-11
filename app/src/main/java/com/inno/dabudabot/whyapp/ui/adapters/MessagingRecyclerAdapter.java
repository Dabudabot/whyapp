package com.inno.dabudabot.whyapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.inno.dabudabot.whyapp.R;

import eventb_prelude.Pair;
import group_6_model_sequential.Content;
import Util.Settings;

import java.util.List;


/**
 * Created by Group-6 on 09.11.17.
 * Manages messages view
 */
public class MessagingRecyclerAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<Content> mMessages;

    public MessagingRecyclerAdapter(List<Content> messages) {
        mMessages = messages;
    }

    public void add(Integer messageId) {
        mMessages.add(Settings.getInstance().getContents().get(messageId));
        notifyItemInserted(mMessages.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_messaging_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_messaging_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Content current = mMessages.get(position);
        Integer myId = Settings.getInstance().getCurrentUser().getId();
        for (Pair<Integer, Integer> ownership :
                Settings.getInstance().getMyMachine().get_owner()) {
            if (ownership.fst().equals(current.getId())
                    && ownership.snd().equals(myId)) {
                configureMyChatViewHolder((MyChatViewHolder) holder, position);
            } else if (ownership.fst().equals(current.getId())
                    && !ownership.snd().equals(myId)) {
                configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
            }
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        Content message = mMessages.get(position);

        myChatViewHolder.txtMessageText.setText(message.getMessage());
        myChatViewHolder.txtMessageTime.setText(message.getTime());
    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        Content message = mMessages.get(position);

        otherChatViewHolder.txtMessageText.setText(message.getMessage());
        otherChatViewHolder.txtMessageTime.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        if (mMessages != null) {
            return mMessages.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Content current = mMessages.get(position);
        Integer myId = Settings.getInstance().getCurrentUser().getId();
        for (Pair<Integer, Integer> ownership :
                Settings.getInstance().getMyMachine().get_owner()) {
            if (ownership.fst().equals(current.getId())
                    && ownership.snd().equals(myId)) {
                return VIEW_TYPE_ME;
            } else if (ownership.fst().equals(current.getId())
                    && !ownership.snd().equals(myId)) {
                return VIEW_TYPE_OTHER;
            }
        }
        return VIEW_TYPE_OTHER;
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMessageTime, txtMessageText;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtMessageTime = (TextView) itemView.findViewById(R.id.message_time);
            txtMessageText = (TextView) itemView.findViewById(R.id.message_text);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMessageTime, txtMessageText;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtMessageTime = (TextView) itemView.findViewById(R.id.message_time);
            txtMessageText = (TextView) itemView.findViewById(R.id.message_text);
        }
    }
}
