package com.example.libros_mbapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class CamaraAc extends AppCompatActivity {


    Button btn_Captura;
    ImageView imgForm;
    private  ImageView atras;
    String rutaImg;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camara);

        btn_Captura =(Button) findViewById(R.id.btnphoto);
        imgForm = (ImageView) findViewById(R.id.imageView6);
        atras = (ImageButton)findViewById(R.id.atras1);




        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            setupCameraCapture();
        }
        this.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File imgArchivo=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    imgArchivo=crearImagen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(imgArchivo!=null){
                    Uri fotoUri = FileProvider.getUriForFile(CamaraAc.this,"com.example.libros_mbapp.fileprovider",imgArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                }
                cameraLan.launch(intent);


            }
        });
    }
    private void setupCameraCapture() {
        // Realizar configuración de la cámara si es necesario
        btn_Captura.setEnabled(true);
    }

    ActivityResultLauncher<Intent> cameraLan=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                Bitmap image= BitmapFactory.decodeFile(rutaImg);
                imgForm.setImageBitmap(image);
                Toast.makeText(CamaraAc.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();

                // Volver a cargar la vista de la cámara
                recreate();
            }
        }
    });

    private File crearImagen() throws IOException {
        String nombreImagen="foto_";
        File carpeta=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img=File.createTempFile(nombreImagen,".jpg",carpeta);
        rutaImg=img.getAbsolutePath();
        return img;
    }

    ActivityResultLauncher<Intent> LanzaCamara= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Bundle extras = result.getData().getExtras();
                Bitmap imagen= (Bitmap) extras.get("data");
                imgForm.setImageBitmap(imagen);
            }
        }
    });@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCameraCapture();
            } else {
                Toast.makeText(CamaraAc.this, "Acesso de camara demegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
