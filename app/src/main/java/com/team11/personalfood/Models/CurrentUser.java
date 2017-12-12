package com.team11.personalfood.Models;


import java.io.Serializable;

public class CurrentUser implements Serializable {

    private String userID ;
    private String password;
    private String name;
    private String birth;
    private String type;


    public CurrentUser(){
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
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
