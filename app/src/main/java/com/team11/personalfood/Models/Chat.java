package com.team11.personalfood.Models;


public class Chat {
    private String userId;
    private String userType;
    private String message;


    public Chat(String name, String type, String message) {
        this.userId = name;
        this.userType = type;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public String getMessage() {
        return message;
    }

}
