package com.eriochrome.bartime.vistas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.presenters.DatosBarPrincipalPresenter;

import java.io.IOException;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarPrincipalActivity extends AppCompatActivity implements DatosBarPrincipalContract.View {

    //TODO: falta ubicacion (acordarse de ponerlo en las verificaciones)
    //TODO: falta enviar la foto/uri/loquesea

    private DatosBarPrincipalPresenter presenter;

    private static final int NUMERO_SOLICITUD_GALERIA = 2;
    private static final int CODIGO_REQUEST_EXTERNAL_STORAGE = 100;

    private Button continuar;
    private Button seleccionarImagen;

    private EditText nombreBar;
    private EditText descripcion;
    private ImageView imagenBar;

    Uri returnUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_principal);

        presenter = new DatosBarPrincipalPresenter();
        presenter.bind(this);

        nombreBar = findViewById(R.id.nombre_bar);
        descripcion = findViewById(R.id.desc);
        seleccionarImagen = findViewById(R.id.seleccionar_imagen_principal);
        imagenBar = findViewById(R.id.imagen_bar);
        continuar = findViewById(R.id.continuar);

        nombreBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) nombreBar.setHint("");
            else nombreBar.setHint(getResources().getString(R.string.nombre));
        });

        descripcion.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) descripcion.setHint("");
            else descripcion.setHint(getResources().getString(R.string.descripcion));
        });

        seleccionarImagen.setOnClickListener(v -> {
            if(tienePermisos())
                seleccionarImagenDeGaleria();
            else
                pedirPermisosUbicacion();
        });

        continuar.setOnClickListener(v -> {
            if (datosListos()) {
                Intent i = new Intent(DatosBarPrincipalActivity.this, DatosBarHorariosActivity.class);
                presenter.setNombre(nombreBar.getText().toString());
                presenter.setDescripcion(descripcion.getText().toString());
                presenter.setUbicacion("Mock text");
                i = presenter.enviarBar(i);
                startActivity(i);
            }
        });
    }

    private void seleccionarImagenDeGaleria() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, NUMERO_SOLICITUD_GALERIA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUMERO_SOLICITUD_GALERIA) {
            if (resultCode == RESULT_OK) {
                returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagenBar.setImageBitmap(bitmapImage);
            } else {
                toastShort(this, "No elegiste ninguna imagen.");
            }
        }
    }

    private boolean tienePermisos() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
    }


    private void pedirPermisosUbicacion() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, CODIGO_REQUEST_EXTERNAL_STORAGE);
    }

    private boolean datosListos() {
        boolean listo = true;
        if (nombreBar.getText().toString().equals("") || nombreBar.getText().toString().equals("Nombre")) {
            listo = false; toastShort(this, "Falta el nombre del bar!");
        }
        else if (descripcion.getText().toString().equals("") || descripcion.getText().toString().equals("Descripcion")) {
            listo = false; toastShort(this, "Falta una descripcion para el bar!");
        }
        //Ubicacion
        else if (returnUri == null) {
            listo = false; toastShort(this, "Se debe asignar una imagen principal para el bar!");
        }
        return listo;
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
