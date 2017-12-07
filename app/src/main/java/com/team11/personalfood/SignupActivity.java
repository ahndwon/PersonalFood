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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";

    private Button registerButton;

    private EditText mFieldId;
    private EditText mFieldName;
    private EditText mFieldPassword;
    private EditText mFieldBirth;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        registerButton = findViewById(R.id.btn_register);
        mFieldId = findViewById(R.id.field_id);
        mFieldName = findViewById(R.id.field_name);
        mFieldPassword = findViewById(R.id.field_password);
        mFieldBirth = findViewById(R.id.field_birth);

        calendar = Calendar.getInstance();

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
                new DatePickerDialog(SignupActivity.this,  date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void onRegisterBtnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
    }




    private void updateDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mFieldBirth.setText(sdf.format(calendar.getTime()));
    }
}
