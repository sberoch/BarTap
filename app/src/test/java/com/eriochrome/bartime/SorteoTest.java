package com.eriochrome.bartime;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


public class SorteoTest {

    @Test
    public void totalDePuntosEstaBien() {
        int totalPuntos = 0;
        HashMap<String, Integer> participantes = new HashMap<>();
        participantes.put("A", 2);
        participantes.put("B", 0);
        for (Integer i : participantes.values()) {
            totalPuntos += (1 + i);
        }
        assertEquals(4, totalPuntos);
    }
}
