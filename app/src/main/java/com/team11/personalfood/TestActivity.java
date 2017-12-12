package com.team11.personalfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.team11.personalfood.Models.Test;

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    private TextView questionText;
    private Button taeYangAnswerButton;
    private Button taeEumAnswerButton;
    private Button soYangAnswerButton;
    private Button soEumAnswerButton;


    private Test[] tests = new Test[]{
            new Test(R.string.question1, R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4),
            new Test(R.string.question2, R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4),
            new Test(R.string.question3, R.string.answer3_1, R.string.answer3_2, R.string.answer3_3, R.string.answer3_4),
            new Test(R.string.question4, R.string.answer4_1, R.string.answer4_2, R.string.answer4_3, R.string.answer4_4),
            new Test(R.string.question5, R.string.answer5_1, R.string.answer5_2, R.string.answer5_3, R.string.answer5_4),
            new Test(R.string.question6, R.string.answer6_1, R.string.answer6_2, R.string.answer6_3, R.string.answer6_4),
            new Test(R.string.question7, R.string.answer7_1, R.string.answer7_2, R.string.answer7_3, R.string.answer7_4),
            new Test(R.string.question8, R.string.answer8_1, R.string.answer8_2, R.string.answer8_3, R.string.answer8_4),
            new Test(R.string.question9, R.string.answer9_1, R.string.answer9_2, R.string.answer9_3, R.string.answer9_4),
            new Test(R.string.question10, R.string.answer10_1, R.string.answer10_2, R.string.answer10_3, R.string.answer10_4),
            new Test(R.string.question11, R.string.answer11_1, R.string.answer11_2, R.string.answer11_3, R.string.answer11_4),
            new Test(R.string.question12, R.string.answer12_1, R.string.answer12_2, R.string.answer12_3, R.string.answer12_4),
            new Test(R.string.question13, R.string.answer13_1, R.string.answer13_2, R.string.answer13_3, R.string.answer13_4),
            new Test(R.string.question14, R.string.answer14_1, R.string.answer14_2, R.string.answer14_3, R.string.answer14_4),
            new Test(R.string.question15, R.string.answer15_1, R.string.answer15_2, R.string.answer15_3, R.string.answer15_4),
            new Test(R.string.question16, R.string.answer16_1, R.string.answer16_2, R.string.answer16_3, R.string.answer16_4),
            new Test(R.string.question17, R.string.answer17_1, R.string.answer17_2, R.string.answer17_3, R.string.answer17_4),
            new Test(R.string.question18, R.string.answer18_1, R.string.answer18_2, R.string.answer18_3, R.string.answer18_4),
            new Test(R.string.question19, R.string.answer19_1, R.string.answer19_2, R.string.answer19_3, R.string.answer19_4),
            new Test(R.string.question20, R.string.answer20_1, R.string.answer20_2, R.string.answer20_3, R.string.answer20_4),
            new Test(R.string.question21, R.string.answer21_1, R.string.answer21_2, R.string.answer21_3, R.string.answer21_4),
            new Test(R.string.question22, R.string.answer22_1, R.string.answer22_2, R.string.answer22_3, R.string.answer22_4),
    };

    private int currentQuestion;
    private static final int TAEYANG = 1;
    private static final int TAEEUM = 2;
    private static final int SOYANG = 3;
    private static final int SOEUM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        questionText = findViewById(R.id.question_text);
        taeYangAnswerButton = findViewById(R.id.taeYang_button);
        taeEumAnswerButton = findViewById(R.id.taeEum_button);
        soYangAnswerButton = findViewById(R.id.soYang_button);
        soEumAnswerButton = findViewById(R.id.soEum_button);

        Toolbar toolbar = findViewById(R.id.test_toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);

        taeYangAnswerButton.setOnClickListener(view -> {
            tests[currentQuestion].setType(TAEYANG);
            currentQuestion++;
            if(currentQuestion == tests.length){
                resultTest();
            }else
                updateQuestion();
        });

        taeEumAnswerButton.setOnClickListener(view -> {
            tests[currentQuestion].setType(TAEEUM);
            currentQuestion++;
            if(currentQuestion == tests.length){
                resultTest();
            }else
                updateQuestion();
        });

        soYangAnswerButton.setOnClickListener(view -> {
            tests[currentQuestion].setType(SOYANG);
            currentQuestion++;
            if(currentQuestion == tests.length){
                resultTest();
            }else
                updateQuestion();
        });

        soEumAnswerButton.setOnClickListener(view -> {
            tests[currentQuestion].setType(SOEUM);
            currentQuestion++;
            if(currentQuestion == tests.length){
                resultTest();
            }else
                updateQuestion();
        });

        updateQuestion();
    }

    private void updateQuestion() {
        int question = tests[currentQuestion].getQuestionId();
        int taeYangAnswer = tests[currentQuestion].getAnswer1Id();
        int taeEumAnswer = tests[currentQuestion].getAnswer2Id();
        int soYangAnswer = tests[currentQuestion].getAnswer3Id();
        int soEumAnswer = tests[currentQuestion].getAnswer4Id();

        questionText.setText(question);
        taeYangAnswerButton.setText(taeYangAnswer);
        taeEumAnswerButton.setText(taeEumAnswer);
        soYangAnswerButton.setText(soYangAnswer);
        soEumAnswerButton.setText(soEumAnswer);

    }

    private void resultTest(){
        int taeYang = 0;
        int taeEum = 0;
        int soYang = 0;
        int soEum = 0;
        int type;

        for(Test test : tests){
            switch (test.getType()){
                case TAEYANG:
                    taeYang++;
                    break;
                case TAEEUM:
                    taeEum++;
                    break;
                case SOYANG:
                    soYang++;
                    break;
                case SOEUM:
                    soEum++;
                    break;
            }
        }

        if(taeYang >= taeEum && taeYang >= soYang && taeYang >= soEum){
            type = TAEYANG;
        } else if (taeEum >= taeYang && taeEum >= soYang && taeEum >= soEum){
            type = TAEEUM;
        } else if (soYang >= taeYang && soYang >= taeEum && soYang >= soEum) {
            type = SOYANG;
        } else
            type = SOEUM;

        System.out.println(type);

        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("Type", type);
        startActivity(intent);

        currentQuestion = 0;

    }


}
