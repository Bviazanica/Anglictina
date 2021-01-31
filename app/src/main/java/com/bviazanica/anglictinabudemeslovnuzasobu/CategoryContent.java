package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CategoryContent extends ListActivity {
    String category;
    Boolean engLocale;
    long category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);
        category_id = getIntent().getLongExtra("category_id", 0);
        category = getIntent().getStringExtra("category");
        engLocale = getIntent().getBooleanExtra("engLocale", false);
        TextView title = findViewById(R.id.category_content_txt);
        title.setText(category);
        System.out.println(category + " " + category_id);
    }

}