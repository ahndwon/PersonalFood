package com.team11.personalfood.Utilities;

import com.team11.personalfood.Models.Chat;

import java.util.List;


public interface OnChatLoadListener {
    void onFetchChat(List<Chat> chatList);
    void onAddChat(Chat chat);
}
