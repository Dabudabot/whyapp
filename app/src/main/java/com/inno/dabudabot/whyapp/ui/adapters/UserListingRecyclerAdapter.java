package com.inno.dabudabot.whyapp.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inno.dabudabot.whyapp.R;

import Util.Settings;
import eventb_prelude.Pair;
import group_6_model_sequential.User;
import group_6_model_sequential.machine3;

import java.util.List;


/**
 * Created by Group-6 on 09.11.17.
 * Manages users list on view
 */
public class UserListingRecyclerAdapter
        extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {
    private List<User> mUsers;

    public UserListingRecyclerAdapter(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_listing,
                        parent,
                        false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);
        if (user.getName() != null) {

            machine3 machine = Settings.getInstance().getMachine();
            String alphabet = user.getName().substring(0, 1);
            int back = R.drawable.circle_accent;

            for (Pair<Integer, Integer> pair : machine.get_toread()) {
                if (pair.snd().equals(user.getId())) {
                    alphabet = "!";
                    back = R.drawable.circle_accent_warn;
                }
            }
            for (Pair<Integer, Integer> pair : machine.get_muted()) {
                if (pair.snd().equals(user.getId())
                        || pair.fst().equals(user.getId())) {
                    alphabet = "M";
                    back = R.drawable.circle_accent_muted;
                }
            }

            holder.txtUsername.setText(user.getName());
            holder.txtUserAlphabet.setText(alphabet);
            holder.txtUserAlphabet.setBackgroundResource(back);
        }
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }

    public User getUser(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserAlphabet, txtUsername;

        ViewHolder(View itemView) {
            super(itemView);
            txtUserAlphabet =
                    (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
            txtUsername =
                    (TextView) itemView.findViewById(R.id.text_view_username);
        }
    }
}
