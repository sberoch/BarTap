package com.eriochrome.bartime;

import android.widget.TextView;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parseoTextViewANumero() {
        String string = "8 - 22";
        String[] splitted = string.split(" - ");
        assertEquals((Integer) 8, Integer.valueOf(splitted[0]));
    }
}
