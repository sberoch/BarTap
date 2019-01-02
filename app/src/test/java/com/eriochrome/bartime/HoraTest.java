package com.eriochrome.bartime;

import com.eriochrome.bartime.utils.Utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class HoraTest {

    @Test
    public void horaEstaEntreMedio() {
        Calendar ahora = Calendar.getInstance();
        ahora.setTime(new Date());
        assertTrue(Utils.estaEntreHoras(19,21, ahora));
    }

    @Test
    public void horaNoEstaEntreMedio() {
        Calendar ahora = Calendar.getInstance();
        ahora.set(Calendar.HOUR_OF_DAY, 11);
        assertFalse(Utils.estaEntreHoras(0,1, ahora));
    }

    @Test
    public void horasRaras() {
        Calendar ahora = Calendar.getInstance();
        ahora.set(Calendar.HOUR_OF_DAY, 11);
        assertFalse(Utils.estaEntreHoras(999,999, ahora));
    }

    @Test
    public void horaNocturna() {
        Calendar ahora = Calendar.getInstance();
        ahora.set(Calendar.HOUR_OF_DAY, 2);
        assertTrue(Utils.estaEntreHoras(23, 3, ahora));
    }
}
