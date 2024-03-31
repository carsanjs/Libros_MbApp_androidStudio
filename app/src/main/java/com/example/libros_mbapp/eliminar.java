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

public class eliminar extends AppCompatActivity {

    private TextView codeFind, nameFind, authorFind, dateFind, obsFind, priceFind;
    private Button btnFind, deleteBtn, cancelDeleteBtn;
    private DatabaseReference database;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);

        this.codeFind = findViewById(R.id.codeFind);
        this.nameFind = findViewById(R.id.nameFind);
        this.authorFind = findViewById(R.id.authorFind);
        this.dateFind = findViewById(R.id.dateFind);
        this.obsFind = findViewById(R.id.obsFind);
        this.priceFind = findViewById(R.id.priceFind);
        this.floatingActionButton = findViewById(R.id.atras21);

        this.database = FirebaseDatabase.getInstance().getReference("libros");


        this.cancelDeleteBtn = (Button) findViewById(R.id.cancelDeleteBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(eliminar.this, fragment_index_home.class));
            }
        });
        this.cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.btnFind = (Button) findViewById(R.id.findBtn);

        btnFind.setOnClickListener(View ->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("libros");
            myRef.child(codeFind.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!codeFind.getText().toString().isEmpty() && snapshot.exists()) {
                        String Nombre = snapshot.child("name").getValue().toString();
                        String Autor = snapshot.child("author").getValue().toString();
                        String Fecha = snapshot.child("date").getValue().toString();
                        String observations = snapshot.child("observations").getValue().toString();
                        String price = snapshot.child("price").getValue().toString();
                        nameFind.setText(Nombre);
                        authorFind.setText(Autor);
                        dateFind.setText(Fecha);
                        obsFind.setText(observations);
                        priceFind.setText(price);
                    }

                    if(codeFind.getText().toString().isEmpty()){
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

        this.deleteBtn = (Button) findViewById(R.id.deleteBtn);
        this.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = codeFind.getText().toString();

                if (code.isEmpty()){
                    CharSequence text = "El campo codigo no puede estar vacio";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("libros");
                    db.child(code).removeValue();
                    CharSequence text = "Se ha eliminado el libro correctamente";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });
    }
    private void clear(){
        codeFind.setText("");
        nameFind.setText("");
        authorFind.setText("");
        dateFind.setText("");
        obsFind.setText("");
        priceFind.setText("");
    }
}