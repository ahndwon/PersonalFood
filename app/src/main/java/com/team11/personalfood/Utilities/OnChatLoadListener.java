package com.team11.personalfood.Utilities;

import com.team11.personalfood.Models.Chat;

import java.util.List;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public interface OnChatLoadListener {
    void onFetchChat(List<Chat> chatList);
}
