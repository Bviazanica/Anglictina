package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryContent extends AppCompatActivity {
    String category;
    String category_id;
    Boolean engLocale = false;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);

        TextView title = findViewById(R.id.category_content_txt);
        category = getIntent().getStringExtra("category");
        category_id = getIntent().getStringExtra("category_id");
        title.setText(category);
        ArrayList<HashMap<String, String>> records = dbHelper.viewCategoryRecords(category_id);
        engLocale = getIntent().getBooleanExtra("engLocale", false);

        lv = findViewById(R.id.records);
        lv.setTextFilterEnabled(true);
        ListAdapter adapter;
        if (!engLocale) {
            adapter = new SimpleAdapter(this, records, R.layout.item, new String[]{"string_sk", "_id"}, new int[]{R.id.string_sk, R.id.string_id});
        } else {
            adapter = new SimpleAdapter(this, records, R.layout.item, new String[]{"string_eng", "_id"}, new int[]{R.id.string_eng, R.id.string_id});
        }
        lv.setAdapter(adapter);
    }
}