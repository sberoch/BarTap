package com.eriochrome.bartime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.eriochrome.bartime.utils.Utils.*;

public class AgregarBarUsuario extends AppCompatActivity {

    private TextView volver;
    private Button listo;
    private CircleImageView imagenBar;
    private EditText nombre;
    private TextView botonFoto;

    final DatabaseReference baresRef = FirebaseDatabase.getInstance().getReference().child("bares");

    int NUMERO_SOLICITUD_GALERIA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bar_usuario);

        volver = findViewById(R.id.volver);
        nombre = findViewById(R.id.nombre);
        botonFoto = findViewById(R.id.foto);
        imagenBar = findViewById(R.id.imagen_bar);
        listo = findViewById(R.id.listo);

        volver.setOnClickListener(v -> {
            finish();
        });
        listo.setOnClickListener(v -> {
            String nombreBar = nombre.getText().toString();
            Bar nuevoBar = new Bar(nombreBar);
            agregarBar(nuevoBar);
            //TODO: cambiar por la clase de confirmacion o lo que sea.
            Intent i = new Intent(AgregarBarUsuario.this, ListadoBares.class);
            startActivity(i);
        });
        botonFoto.setOnClickListener(v -> {
            seleccionarImagenDeGaleria();
        });
    }


    private void seleccionarImagenDeGaleria() {
        Intent elegirFotoIntent = new Intent(Intent.ACTION_PICK);
        elegirFotoIntent.setType("image/*");
        startActivityForResult(elegirFotoIntent, NUMERO_SOLICITUD_GALERIA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imagenBar.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                toastShort(AgregarBarUsuario.this, "Algo fallo");
            }
        }
        else {
            toastShort(AgregarBarUsuario.this, "No elegiste ninguna imagen.");
        }
    }


    private void agregarBar(Bar nuevoBar) {
        //TODO: como agrego al final?
        baresRef.child("6").setValue(nuevoBar, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                toastShort(AgregarBarUsuario.this, "No se pudo guardar el bar " + databaseError.getMessage());
            } else {
                toastShort(AgregarBarUsuario.this,"Se envio el bar con exito.");
            }
        });
    }
}
