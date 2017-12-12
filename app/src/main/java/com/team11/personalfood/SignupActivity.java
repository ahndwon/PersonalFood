package com.team11.personalfood;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.OnLoginListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignupActivity extends BaseActivity implements OnLoginListener {

    //Constants
    private static final String TAG = "SignupActivity";
    private static final int TAEYANG = 1;
    private static final int TAEEUM = 2;
    private static final int SOYANG = 3;
    private static final int SOEUM = 4;

    //View
    public static TextView mErrorText;

    private EditText mFieldId;
    private EditText mFieldName;
    private EditText mFieldPassword;
    private EditText mFieldBirth;
    private String userType = "태양인";

    private Calendar calendar;
    private Date convertedDate;

    private String userId;
    private String password;
    private String name;
    private String birth;

    private Client client;

    private CurrentUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFieldId = findViewById(R.id.field_id);
        mFieldName = findViewById(R.id.field_name);
        mFieldPassword = findViewById(R.id.field_password);
        mFieldBirth = findViewById(R.id.field_birth);

        client = new Client(this);

        calendar = Calendar.getInstance();
        client = new Client(this);

        mErrorText = findViewById(R.id.textview_error);

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

    }

    public void onSignupBtnClick(View view) {

        if(!validateForm()) {
            return;
        }
        userId = mFieldId.getText().toString();
        password = mFieldPassword.getText().toString();
        name = mFieldName.getText().toString();
        birth = mFieldBirth.getText().toString();
        setType();

        client.startSignup(userId, password, name, birth, userType);
        client.startLogin(userId, password, this);

        mFieldId.setText("");
        mFieldPassword.setText("");
        mFieldName.setText("");
        mFieldBirth.setText("");
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


    private void updateDateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mFieldBirth.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onSuccess(CurrentUser user) {
        Intent intent = new Intent(getApplicationContext(),ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        System.out.println("SignUp Auto Login"+user.getName());
    }

    //정보입력 유무 체크
    private boolean validateForm() {
        boolean valid = true;

        String id = mFieldId.getText().toString();
        if (TextUtils.isEmpty(id)) {
            mFieldId.setError("아이디를 입력해주세요");
            valid = false;
        } else {
            mFieldId.setError(null);
        }

        String password = mFieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mFieldPassword.setError("비밀번호를 입력해주세요");
            valid = false;
        } else {
            mFieldPassword.setError(null);
        }

        String name = mFieldName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mFieldName.setError("이름을 입력해주세요");
            valid = false;
        } else {
            mFieldName.setError(null);
        }

        String birth = mFieldBirth.getText().toString();
        if (TextUtils.isEmpty(birth)) {
            mFieldBirth.setError("생일을 입력해주세요");
            valid = false;
        } else {
            mFieldBirth.setError(null);
        }

        return valid;
    }
}
