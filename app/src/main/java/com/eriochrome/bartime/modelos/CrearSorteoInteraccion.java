package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.CrearSorteoContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Sorteo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearSorteoInteraccion implements CrearSorteoContract.Interaccion {

    private DatabaseReference refJuegos;
    private final CrearSorteoContract.Listener listener;
    private Bar bar;

    public CrearSorteoInteraccion(CrearSorteoContract.Listener listener) {
        this.listener = listener;
        refJuegos = FirebaseDatabase.getInstance().getReference().child("juegos");
    }

    @Override
    public void enviarSorteo(String fechaFin, String puntos) {

        Sorteo sorteo = new Sorteo();
        sorteo.setFechaFin(fechaFin);
        sorteo.asignarPuntos(Integer.valueOf(puntos));
        sorteo.asignarNombreBar(bar.getNombre());
        sorteo.asignarTipo();

        String desafioID = refJuegos.push().getKey();
        sorteo.setID(desafioID);

        if (desafioID != null) {
            refJuegos.child("Sorteo").child(desafioID).setValue(sorteo)
                    .addOnSuccessListener(aVoid -> listener.enviado());
        }
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }
}