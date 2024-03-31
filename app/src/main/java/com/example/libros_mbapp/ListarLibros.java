package com.example.libros_mbapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListarLibros extends AppCompatActivity {

    TextView textView3;
    TableLayout tableLayout;
    TextView Casilla1,Casilla2,Casilla3,Casilla4,Casilla5,Casilla6,Casilla7;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        floatingActionButton = findViewById(R.id.atras1);
        textView3 = findViewById(R.id.textView3);
        tableLayout = findViewById(R.id.tableLayout);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListarLibros.this, fragment_index_home.class));
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference libros = database.getReference("libros");

                libros.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        tableLayout.removeAllViews();

                        TableRow headerRow = new TableRow(ListarLibros.this);

                        TextView header1 = new TextView(ListarLibros.this);
                        header1.setText("CODIGO");
                        header1.setBackgroundColor(Color.GRAY);
                        headerRow.addView(header1);

                        TextView header2 = new TextView(ListarLibros.this);
                        header2.setText("Nombre");
                        header2.setBackgroundColor(Color.WHITE);
                        headerRow.addView(header2);

                        TextView header3 = new TextView(ListarLibros.this);
                        header3.setText("AUTOR");
                        header3.setBackgroundColor(Color.GRAY);
                        headerRow.addView(header3);

                        TextView header4 = new TextView(ListarLibros.this);
                        header4.setText("FECHA");
                        header4.setBackgroundColor(Color.WHITE);
                        headerRow.addView(header4);

                        TextView header5 = new TextView(ListarLibros.this);
                        header5.setText("OBSERVACIÓN");
                        header5.setBackgroundColor(Color.GRAY);
                        headerRow.addView(header5);

                        TextView header6 = new TextView(ListarLibros.this);
                        header6.setText("VALOR");
                        header6.setBackgroundColor(Color.WHITE);
                        headerRow.addView(header6);

                        TextView header7 = new TextView(ListarLibros.this);
                        header7.setText("VER MÁS DETALLES");
                        header7.setBackgroundColor(Color.GRAY);
                        headerRow.addView(header7);


                        tableLayout.addView(headerRow);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.exists()) {
                                String id = snapshot.child("code").getValue().toString();
                                String name = snapshot.child("name").getValue().toString();
                                String author = snapshot.child("author").getValue().toString();
                                String date = snapshot.child("date").getValue().toString();
                                String observations = snapshot.child("observations").getValue().toString();
                                String price = snapshot.child("price").getValue().toString();

                                TableRow row = new TableRow(ListarLibros.this);

                                // Agregar texto a la celda
                                Casilla1 = new TextView(ListarLibros.this);
                                Casilla1.setText(id);
                                row.addView(Casilla1);

                                Casilla2 = new TextView(ListarLibros.this);
                                Casilla2.setText(name);
                                row.addView(Casilla2);
                                Casilla3 = new TextView(ListarLibros.this);
                                Casilla3.setText(author);
                                row.addView(Casilla3);
                                Casilla4 = new TextView(ListarLibros.this);
                                Casilla4.setText(date);
                                row.addView(Casilla4);
                                Casilla5 = new TextView(ListarLibros.this);
                                Casilla5.setText(observations);
                                row.addView(Casilla5);
                                Casilla6 = new TextView(ListarLibros.this);
                                Casilla6.setText(price);
                                row.addView(Casilla6);

                                Button btnVerDetalles = new Button(ListarLibros.this);
                                btnVerDetalles.setText("VER MÁS DETALLES");
                                row.addView(btnVerDetalles);

                                btnVerDetalles.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ListarLibros.this, OrdenarProducto.class);
                                        intent.putExtra("valorExtra1", name);
                                        intent.putExtra("valorExtra2", price);
                                        intent.putExtra("valorExtra3", author);
                                        intent.putExtra("valorExtra4", date);
                                        intent.putExtra("valorExtra5", observations);
                                        startActivity(intent);
                                    }
                                });
                                tableLayout.addView(row);


                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String mensaje = "La búsqueda de datos fue cancelada. Error: " + error.getMessage();
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
