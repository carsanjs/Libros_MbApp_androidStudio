package com.example.libros_mbapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class GpsAc extends AppCompatActivity {
    private TextView txt_longitud, txt_latitud;
    private Button btn_coordenadas;
    private ImageView atras;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gps);
        txt_longitud = (TextView) findViewById(R.id.txtlg);
        txt_latitud = (TextView) findViewById(R.id.txtltud);
        btn_coordenadas = (Button) findViewById(R.id.btncordenadas);
        atras = (ImageButton) findViewById(R.id.atras);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ContextCompat.checkSelfPermission(GpsAc.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != (PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }

        this.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_coordenadas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(GpsAc.this, new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess (Location location){

                        if(location!=null){
                            txt_latitud.setText(String.valueOf(location.getLatitude()));
                            txt_longitud.setText(String.valueOf(location.getLongitude()));
                            try {
                                Geocoder geocoder = new Geocoder(GpsAc.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                String address = addresses.get(0).getAddressLine(0);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            txt_latitud.setText("null");
                            txt_longitud.setText("null");
                        }

                    }

                });

            }
        });

    }
}
