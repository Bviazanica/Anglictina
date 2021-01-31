package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Vocabulary extends AppCompatActivity {
    Intent intent;
    TextView category;
    Boolean engLocale;
    ListView lv;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        engLocale = Locale.getDefault().getLanguage().startsWith("en");
        ArrayList<HashMap<String, String>> categoriesList = dbHelper.viewAllCategories();
        if (categoriesList.size() != 0) {
            lv = findViewById(R.id.category_lv);
            lv.setTextFilterEnabled(true);

            lv.setOnItemClickListener((parent, view, position, id) -> {

                System.out.println("toto je id " + id);
                if (!engLocale) {
                    category = view.findViewById(R.id.category_sk);
                } else {
                    category = view.findViewById(R.id.category_eng);
                }
                intent = new Intent(this, CategoryContent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("category", category.getText().toString());
                intent.putExtra("category_id", id);
                intent.putExtra("engLocale", engLocale);
                startActivity(intent);
            });
            ListAdapter adapter;
            if (!engLocale) {
                adapter = new SimpleAdapter(Vocabulary.this, categoriesList, R.layout.item, new String[]{"category_sk"}, new int[]{R.id.category_sk});
            } else {
                adapter = new SimpleAdapter(Vocabulary.this, categoriesList, R.layout.item, new String[]{"category_eng"}, new int[]{R.id.category_eng});
            }
            lv.setAdapter(adapter);

        } else {
            System.out.println("som empty Sadge");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vocabulary_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_category_btn:
                System.out.println(item.toString());
                Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent(this, AddCategory.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("isFromAddExpression", false);
                startActivity(intent);
            default:
                System.out.println("nepoznam");
                return super.onOptionsItemSelected(item);
        }
    }
}