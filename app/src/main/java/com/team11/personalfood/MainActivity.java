package com.team11.personalfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button chat;
    private Button loginButton;
    private Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testButton = findViewById(R.id.test_button);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signUp_button);
    }

    public void onLoginBtnClick(View view) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    public void onSignUpBtnClick(View view){
        Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(intent);
    }
}
