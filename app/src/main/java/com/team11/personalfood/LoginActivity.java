package com.team11.personalfood;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Produce;
import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Post.Communicator;
import com.team11.personalfood.Post.Events.ErrorEvent;
import com.team11.personalfood.Post.Events.ServerEvent;
import com.team11.personalfood.Post.ServerResponse;
import com.team11.personalfood.Utilities.Client;

public class LoginActivity extends BaseActivity {

    private Communicator communicator;
    private Button loginButton;
    private EditText mLogin;
    private EditText mPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        communicator = new Communicator(this);

        mLogin = findViewById(R.id.field_login_id);
        mPassword= findViewById(R.id.field_login_password);
        loginButton = findViewById(R.id.real_login_button);
    }

    public void onRealLoginBtnClick(View view) {
        useLoginPost(mLogin.getText().toString(), mPassword.getText().toString());

        Intent intent = new Intent(getApplicationContext(),ListActivity.class);
        startActivity(intent);
    }

    private void useLoginPost(String username, String password){
        communicator.loginPost(username, password);
    }


    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}
