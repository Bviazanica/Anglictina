package com.bviazanica.anglictinabudemeslovnuzasobu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestResults extends AppCompatActivity {
    ArrayList<Answer> allAnswers;
    ListView listView;
    Button confirmButton;
    Intent intent;
    String selectedTestLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        selectedTestLanguage = getIntent().getStringExtra("selectedTestLanguage");
        allAnswers = (ArrayList<Answer>) getIntent().getSerializableExtra("allAnswers");
        listView = findViewById(R.id.test_results);
        confirmButton = findViewById(R.id.confirm_test_result_btn);
        ArrayAdapter<Answer> adapter = new resultsArrayAdapter(this, 0, allAnswers);
        listView.setAdapter(adapter);
    }

    public void goMainScreen(View view) {
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("isFromAddExpression", false);
        startActivity(intent);
    }

    public void goAgain(View view) {
        intent = new Intent(this, StartTesting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("selectedTestLanguage", selectedTestLanguage);
        startActivity(intent);
    }
}