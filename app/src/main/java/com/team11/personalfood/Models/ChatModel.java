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
        init();
    }

    private void init(){
//        chatList.add(new Chat("영일중안동원", "태음인", "태음인나와라~~!"));
        chatList = new ArrayList<>();
        System.out.println("asdf");
    }

    public void fetchChat(){
        List<Chat> mChatList = new ArrayList<>();
        mChatList = chatList;

        if(onChatLoadListener != null){
            onChatLoadListener.onFetchChat(mChatList);
        }
    }
    public void addChat(String userId, String type, String message){
//        chatList = new ArrayList<>();
        Chat mChat = new Chat(userId,type,message);
        chatList.add(mChat);
        System.out.println(chatList.size());
        printChat();
        fetchChat();

    }
    public void printChat(){
        for(int i = 0; i<chatList.size(); i++){
            Log.d(TAG,"chatList - "+ i +" chat - "+ chatList.get(i).getMessage() + " size - " + chatList.size());
        }
    }
}
