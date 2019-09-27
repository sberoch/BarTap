package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.utils.CustomDireccion;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.eriochrome.bartime.utils.Utils.toastShort;


public class SeleccionarUbicacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int CODIGO_REQUEST_LOCATION = 123;

    private GoogleMap mMap;
    private Geocoder geocoder;
    private final LatLng ubicacionDefault = new LatLng(-34.603722, -58.381592); //Bsas
    private static final int DEFAULT_ZOOM = 15;
    private Location ultimaUbicacionConocida;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;

    private Button listo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ubicacion);

        listo = findViewById(R.id.listo);

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
                }
                @Override
                public void onError(@NonNull Status status) {
                    Log.d("asds", "An error occurred: " + status);
                }
            });

        }
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        listo.setOnClickListener(v -> enviarDatosPosicion());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        getDeviceLocation();
    }


    private String obtenerDireccionConLatLng(LatLng posicion) {
        String aDevolver;
        try {
            List<Address> direcciones = geocoder.getFromLocation(posicion.latitude, posicion.longitude, 1);
            if (direcciones.size() > 0) {
                String calle = direcciones.get(0).getThoroughfare();
                String numero = direcciones.get(0).getSubThoroughfare();
                String ciudad = direcciones.get(0).getLocality();
                String provincia = direcciones.get(0).getAdminArea();
                String pais = direcciones.get(0).getCountryName();
                aDevolver = new CustomDireccion(calle, numero, ciudad, provincia, pais).construir();
            } else {
                aDevolver = getString(R.string.ubicacion_desconocida);
            }
        } catch (IOException e) {
            e.printStackTrace();
            aDevolver = getString(R.string.no_se_puede_obtener_direccion);
        }
        return aDevolver;
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        ultimaUbicacionConocida = task.getResult();
                        if (ultimaUbicacionConocida != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                            ultimaUbicacionConocida.getLatitude(),
                                            ultimaUbicacionConocida.getLongitude()),
                                            DEFAULT_ZOOM));
                        } else {
                            toastShort(SeleccionarUbicacionActivity.this,
                                    getResources().getString(R.string.no_ubicacion_intente_nuevamente));
                        }
                    } else {
                        Log.d("fuck", "Current location is null. Using defaults.");
                        Log.e("fuck", "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionDefault, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                ultimaUbicacionConocida = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == CODIGO_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    //Cuando termino hago el reverse geocoding
    private void enviarDatosPosicion() {
        LatLng posicion = mMap.getCameraPosition().target;
        String direccion = obtenerDireccionConLatLng(posicion);
        Intent data = new Intent();
        data.putExtra("latitud", posicion.latitude);
        data.putExtra("longitud", posicion.longitude);
        data.putExtra("direccion", direccion);
        setResult(RESULT_OK, data);
        finish();
    }
}
