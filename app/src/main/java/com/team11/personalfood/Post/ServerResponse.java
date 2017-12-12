package com.team11.personalfood.Post;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ServerResponse implements Serializable {
    @SerializedName("returned_userID")
    private String userID;
    @SerializedName("returned_password")
    private String password;
    @SerializedName("returned_name")
    private String name;
    @SerializedName("returned_birth")
    private String birth;
    @SerializedName("returned_type")
    private String type;
    @SerializedName("error_code")
    private int errorCode;
    private int status = 1;
    private String error;

    public ServerResponse(String userID, String password, String name, String birth, String type, int errorCode, int status, String error){
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.type = type;
        this.errorCode = errorCode;
        this.status = status;
        this.error = error;
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

    public int getErrorCode() {
        return errorCode;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}