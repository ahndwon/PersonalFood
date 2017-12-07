package com.team11.personalfood.Models;

import com.team11.personalfood.Utilities.OnChatLoadListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class ChatModel {
    private List<Chat> chatList = new ArrayList<>();
    private OnChatLoadListener onChatLoadListener;

    public void setOnChatLoadListener(OnChatLoadListener onChatLoadListener){
        this.onChatLoadListener = onChatLoadListener;
    }

    public ChatModel(){
        init();
    }

    private void init(){
        chatList.add(new Chat("영일중안동원", "태음인", "태음인나와라~~!", 0101));

    }

    public void fetchChat(){
        if(onChatLoadListener != null){
            onChatLoadListener.onFetchChat(chatList);
        }
    }
}
