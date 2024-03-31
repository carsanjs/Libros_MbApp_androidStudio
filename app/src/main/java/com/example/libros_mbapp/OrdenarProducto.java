package com.example.libros_mbapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrdenarProducto extends AppCompatActivity {

    TextView textView25,txttitle_autor,textView42,textView27;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordenarproduc);

        textView25 = findViewById(R.id.textView25);
        button3 = findViewById(R.id.button3);
        txttitle_autor = findViewById(R.id.txttitle_autor);
        textView42 = findViewById(R.id.textView42);
        textView27 = findViewById(R.id.textView27);

        Intent intent = getIntent();
        String name = intent.getStringExtra("valorExtra1");
        String valor = intent.getStringExtra("valorExtra2");
        String autor = intent.getStringExtra("valorExtra3");
        String fecha = intent.getStringExtra("valorExtra4");
        String observaciones = intent.getStringExtra("valorExtra5");


//        textView25 = findViewById(R.id.textView25);
        textView25.setText(String.valueOf(name));
        button3.setText(String.valueOf(valor));
        txttitle_autor.setText(String.valueOf(autor));
        textView42.setText(String.valueOf(fecha));
        textView27.setText(String.valueOf(observaciones));





    }
}
