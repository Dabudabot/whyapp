package com.inno.dabudabot.whyapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.dabudabot.whyapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;



public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<Message> mMessages;

    public ChatRecyclerAdapter(List<Message> messages) {
        mMessages = messages;
    }

    public void add(Message message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(mMessages.get(position).getSenderUid(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        Message message = mMessages.get(position);

        myChatViewHolder.txtMessageText.setText(message.getMessage());
        myChatViewHolder.txtMessageTime.setText(message.getTime());
    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        Message message = mMessages.get(position);

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
        if (TextUtils.equals(mMessages.get(position).getSenderUid(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
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
