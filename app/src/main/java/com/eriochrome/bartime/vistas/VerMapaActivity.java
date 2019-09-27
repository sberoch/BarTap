package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VerMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Bar bar;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    private final int DEFAULT_ZOOM = 16;
    private final float ACCENT_VIOLET_HUE = 285.15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);
        bar = (Bar) getIntent().getSerializableExtra("bar");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(8.0f);

        LatLng barPos = new LatLng(bar.getLat(), bar.getLng());
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(ACCENT_VIOLET_HUE))
                .position(barPos)
                .title(bar.getNombre()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barPos, DEFAULT_ZOOM));
    }
}