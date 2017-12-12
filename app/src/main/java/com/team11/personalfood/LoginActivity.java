package com.team11.personalfood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.OnLoginListener;

public class LoginActivity extends BaseActivity implements OnLoginListener {


    private EditText mLogin;
    private EditText mPassword;
    public static TextView mErrorText;

    private Client client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        client = new Client(this);

        mLogin = findViewById(R.id.field_login_id);
        mPassword= findViewById(R.id.field_login_password);
        Button loginButton = findViewById(R.id.real_login_button);
        mErrorText = findViewById(R.id.textview_error_signup);
    }

    public void onRealLoginBtnClick(View view) {
        if (!validateForm()) {
            return;
        }
        client.startLogin(mLogin.getText().toString(), mPassword.getText().toString(), this);
    }

    @Override
    public void onSuccess(CurrentUser user) {
        Intent intent = new Intent(getApplicationContext(),ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        System.out.println("Login"+user.getName());

    }

    private boolean validateForm() {
        boolean valid = true;

        String id = mLogin.getText().toString();
        if (TextUtils.isEmpty(id)) {
            mLogin.setError("아이디를 입력해주세요");
            valid = false;
        } else {
            mLogin.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("비밀번호를 입력해주세요");
            valid = false;
        } else {
            mPassword.setError(null);
        }
        return valid;
    }

}
