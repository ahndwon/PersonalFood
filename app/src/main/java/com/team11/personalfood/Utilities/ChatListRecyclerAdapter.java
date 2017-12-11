package com.team11.personalfood.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.team11.personalfood.Models.Chat;


public class ChatListRecyclerAdapter extends AbstractRecyclerAdapter<Chat> {
    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatListViewHolder(parent);
    }
}
