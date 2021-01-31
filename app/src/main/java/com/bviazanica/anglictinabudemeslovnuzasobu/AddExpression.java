package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddExpression extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Intent intent;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    EditText sk_string;
    EditText eng_string;
    Spinner categorySpinner;
    Boolean engLocale;
    String cat_string;
    String cat_id;
    String selectedItem_id;
    HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expression);
        engLocale = Locale.getDefault().getLanguage().startsWith("en");
        eng_string = findViewById(R.id.english_expression_txt);
        sk_string = findViewById(R.id.slovak_expression_txt);
        categorySpinner = findViewById(R.id.categories);
        categorySpinner.setOnItemSelectedListener(this);
        ArrayList<HashMap<String, String>> categoriesList = dbHelper.viewAllCategories();
        ArrayList<String> categories = new ArrayList<>();
        data = new HashMap<>();
        for (HashMap<String, String> map : categoriesList)
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String categoryKey = mapEntry.getKey();
                String categoryValues = mapEntry.getValue();
                if (engLocale && categoryKey.equals("category_eng")) {
                    cat_string = categoryValues;
                    categories.add(categoryValues);
                } else if (!engLocale && categoryKey.equals("category_sk")) {
                    cat_string = categoryValues;
                    categories.add(categoryValues);
                } else if (categoryKey.equals("category_id")) {
                    cat_id = categoryValues;
                }
                data.put(cat_string, cat_id);

            }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem_id = data.get(parent.getItemAtPosition(position));
        System.out.println("id je " + selectedItem_id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Nothing selected");
    }

    public void addExpression(View view) {
        HashMap<String, String> query = new HashMap<>();
        query.put(MyDatabaseHelper.COLUMN_STRING_ENG, eng_string.getText().toString());
        query.put(MyDatabaseHelper.COLUMN_STRING_SK, sk_string.getText().toString());
        query.put(MyDatabaseHelper.COLUMN_CATEGORY_ID, selectedItem_id);

        dbHelper.insertString(query);
        Toast.makeText(this, R.string.expression_added, Toast.LENGTH_SHORT).show();
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