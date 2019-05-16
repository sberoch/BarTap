package com.eriochrome.bartime.vistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.BuildConfig;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.utils.CustomDireccion;
import com.google.android.gms.tasks.Task;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SeleccionarUbicacionActivity extends FragmentActivity {

    private static final int DEFAULT_ZOOM = 20;
    private MapView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_seleccionar_ubicacion);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();

        IMapController mapController = map.getController();
        mapController.setZoom(DEFAULT_ZOOM);
        GeoPoint startPoint = new GeoPoint(-34.603722, -58.381592);
        mapController.setCenter(startPoint);
    }
}



//TODO: banda de cosas
/*
public class SeleccionarUbicacionActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int CODIGO_REQUEST_LOCATION = 123;

    private GoogleMap mMap;
    private final LatLng ubicacionDefault = new LatLng(-34.603722, -58.381592); //Bsas
    private static final int DEFAULT_ZOOM = 15;

    private Location ultimaUbicacionConocida;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Geocoder geocoder;

    private LatLng posicion;
    private TextView coorTV;
    private TextView lugarTexto;
    private Button listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ubicacion);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        posicion = new LatLng(0,0);
        coorTV = findViewById(R.id.coordenadas);
        lugarTexto = findViewById(R.id.lugar);
        listo = findViewById(R.id.listo);

        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listo.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("latitud", posicion.latitude);
            data.putExtra("longitud", posicion.longitude);
            data.putExtra("direccion", lugarTexto.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        getDeviceLocation();
        mMap.setOnCameraIdleListener(() -> {
            posicion = mMap.getCameraPosition().target;
            coorTV.setText(posicion.toString());
            obtenerDireccionConLatLng();
        });
    }

    private void obtenerDireccionConLatLng() {
        try {
            List<Address> direcciones = geocoder.getFromLocation(posicion.latitude, posicion.longitude, 1);
            if (direcciones.size() > 0) {
                String calle = direcciones.get(0).getThoroughfare();
                String numero = direcciones.get(0).getSubThoroughfare();
                String ciudad = direcciones.get(0).getLocality();
                String provincia = direcciones.get(0).getAdminArea();
                String pais = direcciones.get(0).getCountryName();
                String aMostrar = new CustomDireccion(calle, numero, ciudad, provincia, pais).construir();
                lugarTexto.setText(aMostrar);
            } else {
                lugarTexto.setText(getString(R.string.no_se_encontro_direccion));
            }
        } catch (IOException e) {
            e.printStackTrace();
            lugarTexto.setText(getString(R.string.no_se_puede_obtener_direccion));
        }
    }


    private void updateLocationUI() {
        try {

            if (tienePermisos()) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                pedirPermisosUbicacion();
            }

        } catch (SecurityException c) {
            Log.e("Exception: %s", c.getMessage());
        }

    }

    private boolean tienePermisos() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED);
    }


    private void pedirPermisosUbicacion() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, CODIGO_REQUEST_LOCATION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODIGO_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLocationUI();
            }
        }
    }


    private void getDeviceLocation() {
        try {
            if (tienePermisos()) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        ultimaUbicacionConocida = task.getResult();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(ultimaUbicacionConocida.getLatitude(),
                                        ultimaUbicacionConocida.getLongitude()), DEFAULT_ZOOM));
                        posicion = mMap.getCameraPosition().target;
                        coorTV.setText(posicion.toString());
                    }
                    else {
                        Log.d("shit", "Current location is null. Using defaults.");
                        Log.e("shit", "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(ubicacionDefault, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });

            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}
*/