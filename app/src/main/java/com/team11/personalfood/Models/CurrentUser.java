package com.team11.personalfood.Models;

/**
 * Created by andong-won on 2017. 12. 11..
 */

public class CurrentUser {

    private String userID;
    private String password;
    private String name;
    private String birth;
    private String type;
    public CurrentUser(String userID, String password, String name, String birth, String type){
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getType() {
        return type;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setType(String type) {
        this.type = type;
    }
}
