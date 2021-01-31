package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class AddCategory extends AppCompatActivity {
    Intent intent;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    EditText eng_category;
    EditText sk_category;
    Boolean isFromAddExpression;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        isFromAddExpression = getIntent().getExtras().getBoolean("isFromAddExpression");
        eng_category = findViewById(R.id.english_category_txt);
        sk_category = findViewById(R.id.slovak_category_txt);
    }

    public void addCategory(View view) {
        HashMap<String, String> query = new HashMap<>();
        query.put(MyDatabaseHelper.COLUMN_CATEGORY_ENG, eng_category.getText().toString());
        query.put(MyDatabaseHelper.COLUMN_CATEGORY_SK, sk_category.getText().toString());

        dbHelper.insertCategory(query);
        Toast.makeText(this, R.string.category_added, Toast.LENGTH_SHORT).show();
        if (isFromAddExpression) {
            intent = new Intent(this, AddExpression.class);
        } else {
            intent = new Intent(this, Vocabulary.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}