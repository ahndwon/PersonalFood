package com.team11.personalfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.OnLoginListener;

public class LoginActivity extends BaseActivity implements OnLoginListener {


    private EditText mLogin;
    private EditText mPassword;

    private Client client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        client = new Client(this);

        mLogin = findViewById(R.id.field_login_id);
        mPassword= findViewById(R.id.field_login_password);
        Button loginButton = findViewById(R.id.real_login_button);
    }

    public void onRealLoginBtnClick(View view) {
        client.startLogin(mLogin.getText().toString(), mPassword.getText().toString(), this);
    }

    @Override
    public void onSuccess(CurrentUser user) {
        Intent intent = new Intent(getApplicationContext(),ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        System.out.println("Login"+user.getName());

    }
}
