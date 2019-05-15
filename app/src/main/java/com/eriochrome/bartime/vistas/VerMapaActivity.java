package com.eriochrome.bartime.vistas;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.BuildConfig;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

//TODO: anda, pero es lento en cargar los tiles

public class VerMapaActivity extends AppCompatActivity {

    private MapView map;
    private Bar bar;

    private final int DEFAULT_ZOOM = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        setContentView(R.layout.activity_ver_mapa);
        bar = (Bar) getIntent().getSerializableExtra("bar");

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();

        IMapController mapController = map.getController();
        mapController.setZoom(DEFAULT_ZOOM);
        GeoPoint startPoint = new GeoPoint( bar.getLat(), bar.getLng());
        mapController.setCenter(startPoint);

        Marker startMarker = new Marker(map);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_place_24dp));
        startMarker.setPosition(startPoint);
        startMarker.setTitle(getString(R.string.ubicacion_del_bar));
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }
}