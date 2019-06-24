package com.eriochrome.bartime.vistas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.utils.FragmentChangeListener;
import com.eriochrome.bartime.utils.GlideApp;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarPrincipalFragment extends Fragment {


    //TODO: falta pasar ubicacion y foto
    //TODO: no anda pedir foto de galeria. Hacerlo con activity de por medio?

    public interface Listener {
        void onReadyPpal(String nombreBar, String desc, String ubicacion);
    }

    private static final int NUMERO_SOLICITUD_GALERIA = 2;
    private static final int CODIGO_REQUEST_EXTERNAL_STORAGE = 100;

    private Button continuar;
    private Button seleccionarImagen;

    private EditText nombreBar;
    private EditText descripcion;
    private ImageView imagenBar;

    private Listener callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_datos_bar_principal, container, false);
        callback = (Listener) getActivity();

        nombreBar = view.findViewById(R.id.nombre_bar);
        descripcion = view.findViewById(R.id.desc);
        seleccionarImagen = view.findViewById(R.id.seleccionar_imagen_principal);
        imagenBar = view.findViewById(R.id.imageview_bar);
        continuar = view.findViewById(R.id.continuar);

        seleccionarImagen.setOnClickListener(v -> {
            if(tienePermisos())
                seleccionarImagenDeGaleria();
            else
                pedirPermisosUbicacion();
        });

        continuar.setOnClickListener(v -> {
            callback.onReadyPpal(nombreBar.getText().toString(),
                                descripcion.getText().toString(),
                                "Una Calle 123, Zarate, Buenos Aires");
            FragmentChangeListener changeListener = (FragmentChangeListener) getActivity();
            changeListener.replaceFragment(new DatosBarFotosFragment());
        });
        return view;
    }

    private void seleccionarImagenDeGaleria() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, NUMERO_SOLICITUD_GALERIA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUMERO_SOLICITUD_GALERIA) {
            if (resultCode == RESULT_OK) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagenBar.setImageBitmap(bitmapImage);
                toastShort(getActivity(), "creo que todo piola");
            } else {
                toastShort(getActivity(), "No elegiste ninguna imagen.");
            }
        }
    }

    private boolean tienePermisos() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
    }


    private void pedirPermisosUbicacion() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, CODIGO_REQUEST_EXTERNAL_STORAGE);
    }
}
