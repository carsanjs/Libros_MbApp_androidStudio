package com.example.libros_mbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_index_home extends AppCompatActivity {

    FloatingActionButton floatingActionButton5,floatingActionButton4,floatingActionButton2,floatingActionButton3;
    ImageButton btncamara, btngps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index_home);

        floatingActionButton5 = findViewById(R.id.floatingActionButton5);
        floatingActionButton4 = findViewById(R.id.floatingActionButton4);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        btncamara = (ImageButton) findViewById(R.id.btncamera);
        btngps = (ImageButton) findViewById(R.id.btnubicacion);


        btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this, CamaraAc.class));
            }
        });

        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this, GpsAc.class));
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this, agregarlibros.class));
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this,eliminar.class));
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this,ListarLibros.class));
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_index_home.this,editar.class));
            }
        });

    }
}