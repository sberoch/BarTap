package com.eriochrome.bartime.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void toastShort(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

}
