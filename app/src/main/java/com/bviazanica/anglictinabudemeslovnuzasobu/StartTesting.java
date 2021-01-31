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
    String resultMessage;
    int answers = 3;
    TextView phrase;
    Boolean rightAnswer;
    ArrayList<Integer> answersIds = new ArrayList<>();
    ArrayList<String> answersStrings = new ArrayList<>();
    ArrayList<String> findAnswer = new ArrayList<>();
    Integer randomId;
    int column_index = 2;
    String userAnswer;
    RadioGroup rbGroup;
    Boolean testEnglish = false;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_testing);
        selectedTestLanguage = getIntent().getStringExtra("selectedTestLanguage");
        if (selectedTestLanguage.equals(getString(R.string.english))) {
            testEnglish = true;
            column_index = 1;
        }
        ArrayList<String> records = dbHelper.getStringIds();
        while (answersIds.size() != answers) {
            Random r = new Random();
            randomId = Integer.parseInt(records.get(r.nextInt(records.size())));
            if (!answersIds.contains(randomId)) {
                answersIds.add(randomId);
            }
        }
        answersStrings = dbHelper.getAnswerStringsById(answersIds, column_index);

        rbGroup = findViewById(R.id.answers_group);
        for (int i = 0; i < rbGroup.getChildCount(); i++) {
            ((RadioButton) rbGroup.getChildAt(i)).setText(answersStrings.get(i));
        }
        rbGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            userAnswer = radioButton.getText().toString();
            System.out.println(userAnswer);
        });
        answer = answersStrings.get(new Random().nextInt(answersStrings.size()));
        findAnswer = dbHelper.findRightAnswer(answer, testEnglish);
        phrase = findViewById(R.id.phrase);
        if (findAnswer.size() != 0) {
            if (testEnglish) {
                phrase.setText(findAnswer.get(1));
            } else
                phrase.setText(findAnswer.get(0));
        }
    }

    public void checkResult(View view) {
        if (testEnglish) {
            if (findAnswer.get(0).equals(userAnswer)) {
                resultMessage = getString(R.string.right);
            } else {
                resultMessage = getString(R.string.wrong);
            }
        } else {
            if (findAnswer.get(1).equals(userAnswer)) {
                resultMessage = getString(R.string.right);
            } else {
                resultMessage = getString(R.string.wrong);
            }
        }
        toast = Toast.makeText(this, resultMessage, Toast.LENGTH_LONG);
        toast.show();
        intent = new Intent(this, StartTesting.class);
        intent.putExtra("selectedTestLanguage", selectedTestLanguage);
        startActivity(intent);
    }
}