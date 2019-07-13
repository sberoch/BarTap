package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.entidades.Juego;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class StrCompareUtils {

    private NormalizedLevenshtein nl;
    private static final double MIN_SIM = 0.6;

    public StrCompareUtils() {
        nl = new NormalizedLevenshtein();
    }

    /**
     * @param juego un juego de un bar
     * @param busqueda query
     * @return true si el query esta contenido en la concatenacion
     *         del tipo de juego y el nombre del bar.
     */
    public boolean juegoContiene(Juego juego, String busqueda) {
        String tipoYBar = juego.getNombreBar().toLowerCase() + juego.getTipoDeJuego().toLowerCase();
        return tipoYBar.contains(busqueda);
    }

    /**
     * @param query el nombre que intenta poner al nuevo bar
     * @param nombreBar un nombre de un bar existente
     * @return true si la semejanza de levenshetein normalizada es mayor a cierta cantidad.
     */
    public boolean sonParecidos(String query, String nombreBar) {
        return nl.similarity(query, nombreBar) >= MIN_SIM;
    }
}
