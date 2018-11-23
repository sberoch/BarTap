package com.eriochrome.bartime.modelos;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.eriochrome.bartime.R;

public class EnviadorDeNotificaciones {

    NotificationCompat.Builder mBuilder;
    private Context context;

    public EnviadorDeNotificaciones(Context context) {
        this.context = context;
        mBuilder = new NotificationCompat.Builder(context, "default");
    }

    public void crearNotificacion(String title, String subtitle) {
        //TODO: mejorar
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(subtitle);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(subtitle));
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void enviarNotificacion() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0, mBuilder.build());
    }
}
