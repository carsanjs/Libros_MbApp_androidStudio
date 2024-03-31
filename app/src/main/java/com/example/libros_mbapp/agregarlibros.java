package com.example.libros_mbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class agregarlibros extends AppCompatActivity {

    private TextView codeBook, nameBook, authorBook, dateBook, obsBook, priceBook;
    private Button addBookBtn, cancelBookBtn;
    private DatabaseReference database;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregarlibros);

        this.codeBook = findViewById(R.id.codeBook);
        this.nameBook = findViewById(R.id.nameBook);
        this.authorBook = findViewById(R.id.authorBoot);
        this.dateBook = findViewById(R.id.dateBook);
        this.obsBook = findViewById(R.id.obsBook);
        this.priceBook = findViewById(R.id.priceBook);
        this.floatingActionButton = findViewById(R.id.atras1);

        this.database = FirebaseDatabase.getInstance().getReference("libros");

        this.cancelBookBtn = (Button) findViewById(R.id.cancelBookBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(agregarlibros.this, fragment_index_home.class));
            }
        });

        this.cancelBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agregarlibros.this, fragment_index_home.class));
            }
        });

        this.addBookBtn = (Button) findViewById(R.id.addBookBtn);
        this.addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code, name, author, date, obs, price;

                code = codeBook.getText().toString();
                name = nameBook.getText().toString();
                author = authorBook.getText().toString();
                date = dateBook.getText().toString();
                obs = obsBook.getText().toString();
                price = priceBook.getText().toString();


                if(!code.isEmpty() && !name.isEmpty() && !author.isEmpty() && !date.isEmpty() && !obs.isEmpty() && !price.isEmpty()){
                    database.child(code).child("code").setValue(code);
                    database.child(code).child("name").setValue(name);
                    database.child(code).child("author").setValue(author);
                    database.child(code).child("date").setValue(date);
                    database.child(code).child("observations").setValue(obs);
                    database.child(code).child("price").setValue(price);

                    CharSequence text = "Libro registrado correctamente!!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                    return;

                }else{
                    CharSequence text = "No se pudo registrar el libro.";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void clear(){
        codeBook.setText("");
        nameBook.setText("");
        authorBook.setText("");
        dateBook.setText("");
        obsBook.setText("");
        priceBook.setText("");
    }
}