package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class StartTesting extends AppCompatActivity {
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    Intent intent;
    String selectedTestLanguage;
    String answer;
    boolean resultMessage;
    int answers = 3;
    TextView phrase;
    TextView progress;
    ArrayList<Integer> answersIds = new ArrayList<>();
    ArrayList<String> answersStrings = new ArrayList<>();
    ArrayList<String> findAnswer = new ArrayList<>();
    Integer randomId;
    int column_index = 2;
    int answer_index = 1;
    String userAnswer;
    RadioGroup rbGroup;
    RadioButton radioButton;
    Boolean testEnglish = false;
    int round = 0;
    ArrayList<Answer> allAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_testing);
        answersIds.clear();
        selectedTestLanguage = getIntent().getStringExtra("selectedTestLanguage");
        if (selectedTestLanguage.equals(getString(R.string.english))) {
            testEnglish = true;
            column_index = 1;
            answer_index = 0;
        }
        progress = findViewById(R.id.progress);
        rbGroup = findViewById(R.id.answers_group);
        phrase = findViewById(R.id.phrase);
        confirmAnswer();
    }

    public void checkResult(View view) {
        if (findAnswer.get(answer_index).equals(userAnswer)) {
            resultMessage = true;
        } else {
            resultMessage = false;
        }
        allAnswers.add(new Answer(userAnswer, phrase.getText().toString(), resultMessage, findAnswer.get(answer_index), String.valueOf(round)));
        confirmAnswer();
        System.out.println(round);
    }

    public void confirmAnswer() {

        if (round == 4) {
            intent = new Intent(this, TestResults.class);
            intent.putExtra("allAnswers", allAnswers);
            intent.putExtra("selectedTestLanguage", selectedTestLanguage);
            startActivity(intent);
        } else {
            round = round + 1;
            progress.setText(String.valueOf(round) + getText(R.string.progress));
            ArrayList<String> records = dbHelper.getStringIds();
            while (answersIds.size() != answers) {
                Random r = new Random();
                randomId = Integer.parseInt(records.get(r.nextInt(records.size())));
                if (!answersIds.contains(randomId)) {
                    answersIds.add(randomId);
                }
            }
            answersStrings = dbHelper.getAnswerStringsById(answersIds, column_index);
            for (int i = 0; i < rbGroup.getChildCount(); i++) {
                ((RadioButton) rbGroup.getChildAt(i)).setText(answersStrings.get(i));
                ((RadioButton) rbGroup.getChildAt(i)).setChecked(false);
            }

            rbGroup.setOnCheckedChangeListener((group, checkedId) -> {
                radioButton = findViewById(checkedId);
                userAnswer = radioButton.getText().toString();
            });
            answer = answersStrings.get(new Random().nextInt(answersStrings.size()));
            findAnswer = dbHelper.findRightAnswer(answer, testEnglish);

            if (findAnswer.size() != 0) {
                if (testEnglish) {
                    phrase.setText(findAnswer.get(1));
                } else
                    phrase.setText(findAnswer.get(0));
            }
        }
        answersIds.clear();

    }
}