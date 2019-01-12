package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.AgregarBarUsuarioContract;
import com.eriochrome.bartime.presenters.AgregarBarUsuarioPresenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.eriochrome.bartime.utils.Utils.*;

public class AgregarBarUsuarioActivity extends AppCompatActivity implements AgregarBarUsuarioContract.View {

    private TextView volver;
    private Button listo;
    private CircleImageView imagenBar;
    private EditText nombre;

    private TextView ubicacion;
    private final int NUMERO_SOLICITUD_UBICACION = 1;
    private double lat;
    private double lng;

    private TextView botonFoto;
    private Uri path;
    private final int NUMERO_SOLICITUD_GALERIA = 2;

    AgregarBarUsuarioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bar_usuario);

        volver = findViewById(R.id.volver);
        nombre = findViewById(R.id.nombre);
        ubicacion = findViewById(R.id.ubicacion);
        botonFoto = findViewById(R.id.foto);
        imagenBar = findViewById(R.id.imagen_bar);
        listo = findViewById(R.id.listo);

        setupListeners();

        presenter = new AgregarBarUsuarioPresenter();
        presenter.bind(this);
    }


    private void setupListeners() {
        volver.setOnClickListener(v -> finish());
        ubicacion.setOnClickListener(v -> {
            Intent intentUbicacion = new Intent(AgregarBarUsuarioActivity.this, MapsActivity.class);
            startActivityForResult(intentUbicacion, NUMERO_SOLICITUD_UBICACION);
        });
        botonFoto.setOnClickListener(v -> seleccionarImagenDeGaleria());
        listo.setOnClickListener(v -> {
            String nombreBar = nombre.getText().toString();
            if (presenter.datosCompletos()) {
                presenter.crearBar(nombreBar);
                presenter.agregarImagen(path);
                presenter.agregarUbicacion(ubicacion.getText().toString(), lat, lng);
                presenter.subirBar();
            }
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

        if (requestCode == NUMERO_SOLICITUD_GALERIA) {
            if (resultCode == RESULT_OK) {
                try {
                    path = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(path);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imagenBar.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    toastShort(AgregarBarUsuarioActivity.this, "Algo fallo");
                }
            }
            else {
                toastShort(AgregarBarUsuarioActivity.this, "No elegiste ninguna imagen.");
            }
        }

        else if (requestCode == NUMERO_SOLICITUD_UBICACION) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    lat = data.getDoubleExtra("latitud", 0);
                    lng = data.getDoubleExtra("longitud", 0);
                    String direccion = data.getStringExtra("direccion");
                    ubicacion.setText(direccion);
                }
            }
        }
    }


    @Override
    public boolean hayImagenSeleccionada() {
        return path != null;
    }

    @Override
    public boolean hayUbicacionSeleccionada() {
        String strUbicacion = ubicacion.getText().toString();
        return (!strUbicacion.equals(getString(R.string.seleccionar_ubicacion))) &&
                (!strUbicacion.equals(""));
    }

    @Override
    public boolean hayNombreValido() {
        return !nombre.getText().toString().equals("");
    }


    @Override
    public void startConfirmacion() {
        Intent i = new Intent(AgregarBarUsuarioActivity.this, ConfirmarNuevoBarActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
