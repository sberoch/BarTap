package com.eriochrome.bartime.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static void toastShort(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }


    public static boolean estaEntreHoras(int horaInicial, int horaFinal, Calendar ahora) {

        //Inicial
        Calendar dateInicial = Calendar.getInstance();
        dateInicial.set(Calendar.HOUR_OF_DAY, horaInicial);
        dateInicial.set(Calendar.MINUTE, 0);

        //Final
        Calendar dateFinal = Calendar.getInstance();
        dateFinal.set(Calendar.HOUR_OF_DAY, horaFinal);
        dateFinal.set(Calendar.MINUTE, 0);

        if(dateFinal.before(dateInicial)) {
            if (ahora.after(dateFinal))
                dateFinal.add(Calendar.DATE, 1);
            else
                dateInicial.add(Calendar.DATE, -1);
        }
        return ahora.after(dateInicial) && ahora.before(dateFinal);
    }


    public static int editTextToInt(EditText editText) {
        return Integer.parseInt(editText.getText().toString());
    }


    public static String getStringDiaDeSemana(Calendar cal) {
        String devolver = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                devolver = "Domingo";
                break;
            case Calendar.MONDAY:
                devolver = "Lunes";
                break;
            case Calendar.TUESDAY:
                devolver = "Martes";
                break;
            case Calendar.WEDNESDAY:
                devolver = "Miercoles";
                break;
            case Calendar.THURSDAY:
                devolver = "Jueves";
                break;
            case Calendar.FRIDAY:
                devolver = "Viernes";
                break;
            case Calendar.SATURDAY:
                devolver = "Sabado";
                break;
        }
        return devolver;
    }

    public static String getNumeroDeFoto(int pos) {
        if (pos == 0) {
            return "";
        } else {
            return ("_" + Integer.toString(pos + 1));
        }
    }
}
