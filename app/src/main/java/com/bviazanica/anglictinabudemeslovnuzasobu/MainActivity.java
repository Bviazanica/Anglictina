package com.bviazanica.anglictinabudemeslovnuzasobu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent = new Intent();
    String selectedTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button[] btn = new Button[3];
        btn[0] = findViewById(R.id.add_expression_btn);
        btn[1] = findViewById(R.id.vocabulary_btn);
        btn[2] = findViewById(R.id.start_test_btn);
        for (int i = 0; i < 3; i++) {
            btn[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.add_expression_btn)) {
            intent = new Intent(MainActivity.this,
                    AddExpression.class);
        } else if (v == findViewById(R.id.vocabulary_btn)) {
            intent = new Intent(MainActivity.this,
                    Vocabulary.class);
        } else if (v == findViewById(R.id.start_test_btn)) {
            showAlertDialog();
        }

    }

    //pridaj slovo => Add expression => modal na kategoriu

    //MOJ SLOVNIK => Vocabulary, po klik na kategoriu => CategoryContent

    //START TESTING bude mat dialog na výber ENG -> SVK ; SVK -> ENG a potom => StartTestingActivity


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
                    System.out.println("selected je " + selectedTest);
                })
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, StartTesting.class);
                    startActivity(intent);
                    System.out.println(selectedTest);
                })

                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

}