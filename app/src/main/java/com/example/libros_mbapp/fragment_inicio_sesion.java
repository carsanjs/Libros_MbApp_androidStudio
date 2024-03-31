package com.example.libros_mbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_inicio_sesion extends AppCompatActivity {

    private TextView loginEmail, loginPass;
    private Button loginBtn, loginForgotBtn, loginRegisterBtn;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inicio_sesion);

        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginPass = findViewById(R.id.loginPass);

        this.database = FirebaseDatabase.getInstance().getReference();

        this.loginForgotBtn = (Button) findViewById(R.id.loginForgotBtn);
        this.loginForgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_inicio_sesion.this, olvidopassword.class);
                startActivity(intent);
            }
        });

        this.loginRegisterBtn = (Button) findViewById(R.id.loginRegisterBtn);
        this.loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_inicio_sesion.this, fragment_registro.class);
                startActivity(intent);
            }
        });

        this.loginBtn = (Button) findViewById(R.id.loginBtn);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String email, password;
                    email = loginEmail.getText().toString();
                    password = loginPass.getText().toString();

                    if (!email.isEmpty() && !password.isEmpty()){
                        DatabaseReference myusers = database.child("usuarios");
                        myusers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                                    if (email.equals(snapshot.child("email").getValue()) && password.equals(snapshot.child("password").getValue())){

                                        CharSequence text = "Login Correcto!!";
                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), fragment_index_home.class);
                                        startActivity(intent);
                                        clear();
                                        return;
                                    }

                                }
                                CharSequence text = "Usuario o contraseña incorrecta.";
                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }catch (Exception e){
                    System.out.print(e.getMessage());
                }

            }
        });
    }
    private void clear(){
        loginEmail.setText("");
        loginPass.setText("");
    }
}