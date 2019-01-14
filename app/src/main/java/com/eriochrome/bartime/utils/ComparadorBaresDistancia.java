package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.Bar;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.Comparator;

public class ComparadorBaresDistancia implements Comparator<Bar> {

    private LatLng latLngUsuario;

    public ComparadorBaresDistancia(LatLng latLngUsuario) {
        this.latLngUsuario = latLngUsuario;
        if (latLngUsuario == null) {
            this.latLngUsuario = new LatLng(-34.603722, -58.381592); //Buenos Aires por defecto
        }
    }
    @Override
    public int compare(Bar o1, Bar o2) {
        double dist1 = SphericalUtil.computeDistanceBetween(latLngUsuario, o1.getLatLng());
        double dist2 = SphericalUtil.computeDistanceBetween(latLngUsuario, o2.getLatLng());

        if (dist1 < dist2) return -1;
        else if (dist1 > dist2) return 1;
        return 0;
    }
}
