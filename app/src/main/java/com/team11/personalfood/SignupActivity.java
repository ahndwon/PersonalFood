package com.team11.personalfood;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Post.Communicator;
import com.team11.personalfood.Utilities.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";
    private static final int TAEYANG = 1;
    private static final int TAEEUM = 2;
    private static final int SOYANG = 3;
    private static final int SOEUM = 4;

    private Button registerButton;

    public CurrentUser currentUser;

    private EditText mFieldId;
    private EditText mFieldName;
    private EditText mFieldPassword;
    private EditText mFieldBirth;
    private String userType = "태양인";
    private Button buttonInsert;

    private Communicator communicator;
    private Calendar calendar;
    private Date convertedDate;

    private String userId;
    private String password;
    private String name;
    private String birth;

    private Client signupClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        communicator = new Communicator(this);

        registerButton = findViewById(R.id.btn_register);
        mFieldId = findViewById(R.id.field_id);
        mFieldName = findViewById(R.id.field_name);
        mFieldPassword = findViewById(R.id.field_password);
        mFieldBirth = findViewById(R.id.field_birth);
        buttonInsert = findViewById(R.id.btn_register);

        calendar = Calendar.getInstance();
        signupClient = new Client(this);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        mFieldBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: mDate");
                new DatePickerDialog(SignupActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = mFieldId.getText().toString();
                password = mFieldPassword.getText().toString();
                name = mFieldName.getText().toString();
                birth = mFieldBirth.getText().toString();
                setType();

//                loginClient.InsertData task = new Client.InsertData();
//                task.execute(userId, password, name, birth, userType);userType
//                signupClient.startSignup(userId, password, name, birth, userType);

                useJoinPost(userId, password,name,birth , userType);

                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);

                mFieldId.setText("");
                mFieldPassword.setText("");
                mFieldName.setText("");
                mFieldBirth.setText("");


            }
        });

//        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
//        Date inputDate = fmt.parse("10-22-2011 01:00");

//        Create the MySQL datetime string
//        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = fmt.format(inputDate);
    }

    public void setType() {
        int type = getIntent().getIntExtra("Type", 0);
        switch (type) {
            case TAEYANG:
                userType = "태양인";
                break;
            case TAEEUM:
                userType = "태음인";
                break;
            case SOYANG:
                userType = "소양인";
                break;
            case SOEUM:
                userType = "소음인";
                break;
        }
    }


//    public void onRegisterBtnClick(View view) {
//        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
//        startActivity(intent);
//    }


    private void updateDateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String selectedDate = sdf.format(calendar.getTime());
        mFieldBirth.setText(sdf.format(calendar.getTime()));


    }

    private void useJoinPost(String username, String password, String name, String birth, String type){
        communicator.joinPost(username, password, name, birth,type);
    }


}
