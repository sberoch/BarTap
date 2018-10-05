package com.eriochrome.bartime;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class EnviadorDeNotificaciones {

    NotificationCompat.Builder mBuilder;
    private Context context;
    private String title = "Bar de prueba";
    private String subtitle = "¡OFERTA! Cerveza 2X1 solo por la proxima hora! Que estas esperando?";

    public EnviadorDeNotificaciones(Context context) {
        this.context = context;
        mBuilder = new NotificationCompat.Builder(context, "default");
    }

    public void crearNotificacion() {
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
