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
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.presenters.DatosBarPrincipalPresenter;
import com.eriochrome.bartime.utils.GlideApp;

import java.io.IOException;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarPrincipalActivity extends AppCompatActivity implements DatosBarPrincipalContract.View {

    //TODO: falta ubicacion (acordarse de ponerlo en las verificaciones)

    private DatosBarPrincipalPresenter presenter;

    private static final int NUMERO_SOLICITUD_GALERIA = 2;
    private static final int CODIGO_REQUEST_EXTERNAL_STORAGE = 100;

    private Button continuar;
    private Button seleccionarUbicacion;
    private Button seleccionarImagen;

    private TextView tituloActivity;
    private EditText nombreBar;
    private EditText descripcion;
    private ImageView imagenBar;

    Uri returnUri;
    private boolean tieneFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_principal);

        presenter = new DatosBarPrincipalPresenter();
        presenter.bind(this);

        tituloActivity = findViewById(R.id.titulo);
        nombreBar = findViewById(R.id.nombre_bar);
        descripcion = findViewById(R.id.desc);
        seleccionarUbicacion = findViewById(R.id.seleccionar_ubicacion);
        seleccionarImagen = findViewById(R.id.seleccionar_imagen_principal);
        imagenBar = findViewById(R.id.imagen_bar);
        continuar = findViewById(R.id.continuar);
        tieneFoto = false;

        presenter.obtenerBar(getIntent());

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
                presenter.setUbicacion("Mock text"); //TODO: ubicacion

                //Ya subo la foto al storage porque total si no se crea el bar despues se puede sobreescribir
                try {
                    if (returnUri != null) {
                        presenter.subirFoto(returnUri);
                    }
                }  catch (RuntimeException e) {
                    toastShort(this, getString(R.string.ocurrio_error_inesperado));
                }
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
                toastShort(this, getString(R.string.no_elegiste_imagen));
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
            listo = false; toastShort(this, getString(R.string.falta_nombre_bar));
        }
        else if (descripcion.getText().toString().equals("") || descripcion.getText().toString().equals("Descripcion")) {
            listo = false; toastShort(this, getString(R.string.falta_descripcion_para_bar));
        }
        //TODO: Ubicacion
        else if (returnUri == null && !tieneFoto) {
            listo = false; toastShort(this, getString(R.string.se_debe_asignar_imagen_principal));
        }
        return listo;
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void setTitleEditar() {
        tituloActivity.setText(getString(R.string.editar_bar));
    }

    @Override
    public void setNombreBar(String nombre) {
        nombreBar.setText(nombre);
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion.setText(descripcion);
    }

    @Override
    public void setUbicacion(String ubicacion) {
        this.seleccionarUbicacion.setText(ubicacion);
    }

    @Override
    public void onImageLoaded(String downloadUrl) {
        GlideApp.with(this)
                .load(downloadUrl)
                .placeholder(R.drawable.barra_progreso_circular)
                .into(imagenBar);
    }

    @Override
    public void tieneFoto() {
        tieneFoto = true;
    }
}
