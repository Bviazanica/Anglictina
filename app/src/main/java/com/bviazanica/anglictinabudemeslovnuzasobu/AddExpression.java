package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddExpression extends AppCompatActivity {

    Intent intent;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    EditText sk_string;
    EditText eng_string;
    Spinner categorySpinner;
    Boolean engLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expression);
        engLocale = Locale.getDefault().getLanguage().startsWith("en");
        eng_string = findViewById(R.id.english_expression_txt);
        sk_string = findViewById(R.id.slovak_expression_txt);
        categorySpinner = findViewById(R.id.categories);
        ArrayList<HashMap<String, String>> categoriesList = dbHelper.viewAllCategories();
        ArrayList<String> categories = new ArrayList<>();
        for (HashMap<String, String> map : categoriesList)
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String categoryKey = mapEntry.getKey();
                String categoryValues = mapEntry.getValue();
                System.out.println(categoryKey + " " + categoryValues);
                if (engLocale && categoryKey.equals("category_eng")) {
                    categories.add(categoryValues);
                } else if (!engLocale && categoryKey.equals("category_sk")) {
                    categories.add(categoryValues);
                }
            }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        categorySpinner.setAdapter(adapter);
    }


    public void addExpression(View view) {
        HashMap<String, String> query = new HashMap<>();
        query.put(MyDatabaseHelper.COLUMN_STRING_ENG, eng_string.getText().toString());
        query.put(MyDatabaseHelper.COLUMN_STRING_SK, sk_string.getText().toString());
//        query.put(MyDatabaseHelper.COLUMN_CATEGORY_ID, )


        intent = new Intent(this, AddExpression.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    public void goToAddCategory(View view) {
        intent = new Intent(this, AddCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("isFromAddExpression", true);
        startActivity(intent);
    }
}