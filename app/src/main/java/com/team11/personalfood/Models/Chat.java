package com.team11.personalfood.Models;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class Chat {
    public String userId;
    public String userType;
    public String message;


    public Chat(String name, String type, String message) {
        this.userId = name;
        this.userType = type;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
