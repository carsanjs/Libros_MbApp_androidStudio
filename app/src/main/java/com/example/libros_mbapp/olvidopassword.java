package com.example.libros_mbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.MoreObjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class olvidopassword extends AppCompatActivity {

    private TextView emailForgot, pass1Forgot, pass2Forgot, registerId;
    private Button cancelForgot, forgotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.olvidopassword);

        this.registerId = findViewById(R.id.emailForgot2);
        this.emailForgot = findViewById(R.id.emailForgot);
        this.pass1Forgot = findViewById(R.id.pass1Forgot);
        this.pass2Forgot = findViewById(R.id.pass2Forgot);


        this.cancelForgot = (Button) findViewById(R.id.cancelForgot);
        this.cancelForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.forgotBtn = (Button) findViewById(R.id.forgotBtn);
        forgotBtn.setOnClickListener(View -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("usuarios");
            //myRef.child(textnumerodocumento.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            //if(myRef.child(textnumerodocumento.getText().toString()).equals(database.getReference().setValue(textnumerodocumento))){
            myRef.child(registerId.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(registerId.getText().toString().isEmpty() ||
                            emailForgot.getText().toString().isEmpty() ||
                            pass1Forgot.getText().toString().isEmpty() || pass2Forgot.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        String mensaje = "INGRESE LOS DATOS";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }else{
                        if (pass1Forgot.getText().toString().equals(pass2Forgot.getText().toString())){
                            Map<String, Object> map = new HashMap<>();

                            map.put("id",registerId.getText().toString());
                            map.put("email", emailForgot.getText().toString());
                            map.put("password", pass1Forgot.getText().toString());
                            map.put("password", pass2Forgot.getText().toString());
                            myRef.child(registerId.getText().toString()).updateChildren(map);

                            Context context = getApplicationContext();
                            String mensaje = "CONTRASEÑA ACTUALIZADA";
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

                            clear();
                        }else{
                            Context context = getApplicationContext();
                            String mensaje = "LAS CONTRASEÑAS NO COINCIDEN.";
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!snapshot.exists()){
                        Context context = getApplicationContext();
                        String mensaje = "ESTE USUARIO NO EXISTE EN LA BASE DE DATOS.";
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
    }
    private void clear(){
        registerId.setText("");
        emailForgot.setText("");
        pass1Forgot.setText("");
        pass2Forgot.setText("");
    }
}