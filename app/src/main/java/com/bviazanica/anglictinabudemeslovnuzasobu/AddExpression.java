package com.bviazanica.anglictinabudemeslovnuzasobu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class AddExpression extends AppCompatActivity {

    //        dropdown hint/ placeholder bude list[0] disabled, plusko nejak zmenit(material design maybe)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expression);
    }
}
