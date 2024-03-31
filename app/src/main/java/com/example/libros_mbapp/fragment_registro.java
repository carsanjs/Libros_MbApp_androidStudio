package com.example.libros_mbapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class fragment_registro extends AppCompatActivity {

    private TextView registerId, registerUser, registerName, registerLast, registerEmail, registerPass;
    private Button registerBtn, registerLoginBtn;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro);

        this.registerId = findViewById(R.id.registerId);
        this.registerUser = findViewById(R.id.registerUser);
        this.registerName = findViewById(R.id.registerName);
        this.registerLast = findViewById(R.id.registerLast);
        this.registerEmail = findViewById(R.id.registerEmail);
        this.registerPass = findViewById(R.id.registerPass);
        this.registerBtn = (Button) findViewById(R.id.registerBtn);
        this.registerLoginBtn = (Button) findViewById(R.id.registerLoginBtn);

        this.database = FirebaseDatabase.getInstance().getReference("usuarios");
        this.registerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_registro.this, fragment_inicio_sesion.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("usuarios");

                if (registerId.getText().toString().isEmpty() || registerUser.getText().toString().isEmpty()
                        || registerName.getText().toString().isEmpty() || registerLast.getText().toString().isEmpty() || registerEmail.getText().toString().isEmpty()
                || registerPass.getText().toString().isEmpty()) {

                    Context context = getApplicationContext();
                    String mensaje = "INGRESE LOS DATOS";
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                }else{

                    Map<String, Object> map = new HashMap<>();

                    map.put("id",registerId.getText().toString());
                    map.put("user",registerUser.getText().toString());
                    map.put("name",registerName.getText().toString());
                    map.put("lastname",registerLast.getText().toString());
                    map.put("email",registerEmail.getText().toString());
                    map.put("password",registerPass.getText().toString());
                    myRef.child(registerId.getText().toString()).updateChildren(map);

                    CharSequence text = "Usuario registrado con éxito";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(fragment_registro.this, fragment_inicio_sesion.class));
                }
            }
        });
    }
}