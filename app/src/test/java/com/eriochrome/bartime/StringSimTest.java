package com.eriochrome.bartime;

import org.junit.Test;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import static org.junit.Assert.*;

public class StringSimTest {

    String[] lista = {"Republica Beer House", "Republica Bar", "Republica casa de cervezas",
            "Republica", "La Republica Beer House", "La Republica", "Publica Bar", "La Publica Bar",
            "Amigos Beer House", "Bar Beer House", "Beer House"};

    @Test
    public void similaridadConLevenshtein() {
        int coincidencias = 0;
        String q = "Republica Beer House";

        NormalizedLevenshtein nl = new NormalizedLevenshtein();

        for (String w : lista) {
            if (nl.similarity(w, q) >= 0.6) {
                coincidencias++;
                System.out.print(w);
            }
        }
        //Este es el mejor creo
        assertEquals(coincidencias, 3);
    }

    @Test
    public void similaridadConCoseno() {
        int coincidencias = 0;
        String q = "Republica Beer House";

        Cosine nl = new Cosine();

        for (String w : lista) {
            if (nl.similarity(w, q) >= 0.6) {
                coincidencias++;
                System.out.print(w);
            }
        }
        //Este da demasiados resultados
        assertEquals(coincidencias, 6);

    }

    @Test
    public void similaridadConJaccard() {
        int coincidencias = 0;
        String q = "Republica Beer House";

        Jaccard nl = new Jaccard();

        for (String w : lista) {
            if (nl.similarity(w, q) >= 0.6) {
                coincidencias++;
                System.out.print(w);
            }
        }
        //Este da pocos resultados
        assertEquals(coincidencias, 2);
    }
}
