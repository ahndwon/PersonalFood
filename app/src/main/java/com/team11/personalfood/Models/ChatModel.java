package com.team11.personalfood.Models;

import android.util.Log;

import com.team11.personalfood.Utilities.OnChatLoadListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class ChatModel {
    private static final String TAG = "ChatModel";

    private List<Chat> chatList;
    private OnChatLoadListener onChatLoadListener;
    private String userId;
    private String type;
    private String message;


    public void setOnChatLoadListener(OnChatLoadListener onChatLoadListener){
        this.onChatLoadListener = onChatLoadListener;
    }

    public ChatModel(){
        chatList = new ArrayList<>();
    }

    private void init(){
//        chatList.add(new Chat("영일중안동원", "태음인", "태음인나와라~~!"));

    }

    public void fetchChat(){
        if(onChatLoadListener != null){
            onChatLoadListener.onFetchChat(chatList);
        }
    }
    public void addChat(String userId, String type, String message){
        chatList.add(new Chat(userId,type,message));
        for(int i = 0; i<chatList.size(); i++){
            Log.d(TAG,"chatList - "+i +" chat-  "+ chatList.get(i));
        }

    }
}
