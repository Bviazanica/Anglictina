package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent = new Intent();
    String selectedTest;
    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    ArrayList<Category> categories;
    ArrayList<Phrase> strings;
    ArrayList<String> helper;
    String json;

    public static boolean isSDCARDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        Button[] btn = new Button[3];
        btn[0] = findViewById(R.id.add_expression_btn);
        btn[1] = findViewById(R.id.vocabulary_btn);
        btn[2] = findViewById(R.id.start_test_btn);
        for (int i = 0; i < 3; i++) {
            btn[i].setOnClickListener(this);
        }

        ArrayList<HashMap<String, String>> allStrings = dbHelper.getAllRecords();
        ArrayList<HashMap<String, String>> categoriesList = dbHelper.viewAllCategories();
        categories = new ArrayList<>();
        strings = new ArrayList<>();
        helper = new ArrayList<>();
        for (HashMap<String, String> map : allStrings) {
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String categoryValues = mapEntry.getValue();
                helper.add(categoryValues);
            }
            strings.add(new Phrase(helper.get(3), helper.get(0), helper.get(1), helper.get(2)));
            helper.clear();
        }
        for (HashMap<String, String> map : categoriesList) {
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                String categoryKey = mapEntry.getKey();
                String categoryValues = mapEntry.getValue();
                helper.add(categoryValues);
            }
            categories.add(new Category(helper.get(2), helper.get(0), helper.get(1), stringById(strings, helper.get(2))));
            helper.clear();
        }
        json = gson.toJson(categories);
        System.out.println(json);
    }

    private boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State", "Yes, it is writable!");
            return true;
        } else {
            return false;
        }
    }

    public void writeFile(MenuItem v) {
        if (isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            File path = this.getExternalFilesDir(null);
            File file = new File(path, "data.json");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(json.getBytes());
                fos.close();
                Toast.makeText(this, "File Saved.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Cannot Write to External Storage.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.add_expression_btn)) {
            intent = new Intent(this,
                    AddExpression.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (v == findViewById(R.id.vocabulary_btn)) {
            intent = new Intent(this,
                    Vocabulary.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (v == findViewById(R.id.start_test_btn)) {
            showAlertDialog();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.import_data:
            case R.id.export_data:
                Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog() {
        String[] testLanguages = {getString(R.string.english), getString(R.string.slovak)};
        selectedTest = getString(R.string.english);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialogTitle))
                .setSingleChoiceItems(testLanguages, 0, (dialog, which) -> {
                    selectedTest = testLanguages[which];
                })
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    intent = new Intent(MainActivity.this, StartTesting.class);
                    intent.putExtra("selectedTestLanguage", selectedTest);
                    startActivity(intent);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    public ArrayList<Phrase> stringById(ArrayList<Phrase> strings, String cat_id) {
        ArrayList<Phrase> str = (ArrayList<Phrase>) strings.clone();
        ArrayList<Phrase> new_list = new ArrayList<>();
        for (Phrase phr : str) {
            if (phr.string_cat_id.equals(cat_id)) {
                new_list.add(phr);
            }
        }
        return new_list;
    }


}