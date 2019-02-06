package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VerMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Bar bar;
    private final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);
        bar = (Bar) getIntent().getSerializableExtra("bar");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ubicacionBar = bar.getLatLng();
        googleMap.addMarker(new MarkerOptions().position(ubicacionBar).title(getString(R.string.ubicacion_del_bar)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionBar, DEFAULT_ZOOM));
    }
}
