package com.team11.personalfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends BaseActivity {
    private static final int TAEYANG = 1;
    private static final int TAEEUM = 2;
    private static final int SOYANG = 3;
    private static final int SOEUM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.result_toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);

        ImageView typeImageView = findViewById(R.id.type_imageView);
        TextView typeTextView = findViewById(R.id.type_text);
        ImageButton nextButton = findViewById(R.id.next_imageView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, SignupActivity.class);
                int type = getIntent().getIntExtra("Type", 0);

                intent.putExtra("Type", type);
                startActivity(intent);
            }
        });

        int type = getIntent().getIntExtra("Type", 0);
        switch (type){
            case TAEYANG:
                typeTextView.setText("태양인");
                typeImageView.setImageResource(R.drawable.taeyang);
                break;
            case TAEEUM:
                typeTextView.setText("태음인");
                typeImageView.setImageResource(R.drawable.taeeum);

                break;
            case SOYANG:
                typeTextView.setText("소양인");
                typeImageView.setImageResource(R.drawable.soyang);

                break;
            case SOEUM:
                typeTextView.setText("소음인");
                typeImageView.setImageResource(R.drawable.soeum);
                break;
        }

    }
}
