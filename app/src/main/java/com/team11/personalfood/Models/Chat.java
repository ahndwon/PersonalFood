package com.team11.personalfood.Models;

/**
 * Created by andong-won on 2017. 12. 7..
 */

public class Chat {
    public String userName;
    public String userType;
    public String message;
    public long time;

    public Chat(String name, String type, String message, long time) {
        this.userName = name;
        this.userType = type;
        this.message = message;
        this.time = time;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
