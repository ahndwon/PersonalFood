package com.team11.personalfood.Utilities;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team11.personalfood.Models.Chat;
import com.team11.personalfood.Models.Food;
import com.team11.personalfood.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class ChatListViewHolder extends AbstractViewHolder<Chat> {
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());
    private TextView userName;
    private TextView userType;
    private TextView message;
    private TextView time;

    public ChatListViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false));

        userName = itemView.findViewById(R.id.chatname_textview);
        userType = itemView.findViewById(R.id.chat_type_textview);
        message = itemView.findViewById(R.id.chat_message_textView);
        time = itemView.findViewById(R.id.chat_time_textView);

        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "210 직장인의한마디R.ttf");
        this.userName.setTypeface(typeface);
        this.userType.setTypeface(typeface);
        this.message.setTypeface(typeface);
    }

    @Override
    public void onBindView(@NonNull Chat item, int position) {
        userName.setText(item.getUserName());
        userType.setText(stringManipulation(item.getUserType()));
        message.setText(item.getMessage());
        time.setText(mSimpleDateFormat.format(item.getTime()));
    }

    public String stringManipulation(String text){
        String temp = "(" + text + ")";
        return temp;
    }
}