package com.team11.personalfood.Models;

import java.util.Date;

/**
 * Created by andong-won on 2017. 12. 11..
 */

public class LoginData {
    final String userID;
    final String password;

    public LoginData(String userID, String password) {
        this.userID =userID;
        this.password = password;
    }
}
