package com.example.libros_mbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editar extends AppCompatActivity {

    private TextView findEditCode, editName, editAuthor, editObs, editDate, editPrice;
    private Button findEditBtn, btnUpdate, btnUpdateCancel;
    private DatabaseReference database;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        this.findEditCode = findViewById(R.id.findEditCode);
        this.editName = findViewById(R.id.editName);
        this.editAuthor = findViewById(R.id.editAuthor);
        this.editObs = findViewById(R.id.editObs);
        this.editDate = findViewById(R.id.editDate);
        this.editPrice = findViewById(R.id.editPrice);
        floatingActionButton = findViewById(R.id.atras1);

        this.database = FirebaseDatabase.getInstance().getReference("libros");

        this.btnUpdateCancel = (Button) findViewById(R.id.btnUpdateCancel);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editar.this, fragment_index_home.class));
            }
        });
        this.btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.findEditBtn = (Button) findViewById(R.id.findEditBtn);

        findEditBtn.setOnClickListener(View ->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("libros");
            myRef.child(findEditCode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!findEditCode.getText().toString().isEmpty() && snapshot.exists()) {
                        String Nombre = snapshot.child("name").getValue().toString();
                        String Autor = snapshot.child("author").getValue().toString();
                        String Fecha = snapshot.child("date").getValue().toString();
                        String observations = snapshot.child("observations").getValue().toString();
                        String price = snapshot.child("price").getValue().toString();
                        editName.setText(Nombre);
                        editAuthor.setText(Autor);
                        editDate.setText(Fecha);
                        editObs.setText(observations);
                        editPrice.setText(price);
                    }

                    if(findEditCode.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        String mensaje = "INGRESE LOS DATOS";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }

                    if(!snapshot.exists()) {
                        clear();
                        Context context = getApplicationContext();
                        String mensaje = "LIBRO NO ENCONTRADO";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    String mensaje = "La búsqueda de datos fue cancelada. Error: " + error.getMessage();
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                }
            });
        });


        this.btnUpdate = (Button) findViewById(R.id.btnUpdate);
        this.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = findEditCode.getText().toString();
                String name, author, date, obs, price;

                name = editName.getText().toString();
                author = editAuthor.getText().toString();
                date = editDate.getText().toString();
                obs = editObs.getText().toString();
                price = editPrice.getText().toString();

                if(!code.isEmpty() && !name.isEmpty() && !author.isEmpty() && !date.isEmpty() && !obs.isEmpty() && !price.isEmpty()){
                    database.child(code).child("name").setValue(name);
                    database.child(code).child("author").setValue(author);
                    database.child(code).child("date").setValue(date);
                    database.child(code).child("observations").setValue(obs);
                    database.child(code).child("price").setValue(price);

                    CharSequence text = "Se ha actualizado el libro correctamente";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                }else {
                    CharSequence text = "Hay campos que se encuentrar vacios";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void clear(){
        findEditCode.setText("");
        editName.setText("");
        editAuthor.setText("");
        editObs.setText("");
        editDate.setText("");
        editPrice.setText("");
    }
}