package com.team11.personalfood;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.team11.personalfood.Utilities.Client;

public class LoginActivity extends BaseActivity {

    private Button loginButton;
    private EditText mLogin;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = findViewById(R.id.list_toolbar);
//        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
//        setSupportActionBar(toolbar);
        mLogin = findViewById(R.id.field_login_id);
        mPassword= findViewById(R.id.field_login_password);
        loginButton = findViewById(R.id.real_login_button);

//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
    }

    public void onRealLoginBtnClick(View view) {
        Client client = new Client();
        client.startLogin();
        if(client.getUserId() == mLogin.getText().toString() &&
                client.getPassword() == mPassword.getText().toString()) {
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            startActivity(intent);
        }



    }
}
