package com.team11.personalfood.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class ChatListRecyclerAdapter extends AbstractRecyclerAdapter {
    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatListViewHolder(parent);
    }
}
