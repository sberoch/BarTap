package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.Juego;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class JuegoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private Juego juego;

    private TextView nombreJuego;
    private TextView barDelJuego;
    private ImageView imagenDelJuego;

    public interface OnJuegoHolderClickListener {
        void onClickJuego(Juego juego);
    }

    private OnJuegoHolderClickListener clickListener;

    public JuegoHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;

        nombreJuego = view.findViewById(R.id.nombre_juego);
        barDelJuego = view.findViewById(R.id.bar_del_juego);
        imagenDelJuego = view.findViewById(R.id.imagen_juego);

        try {
            clickListener = (OnJuegoHolderClickListener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }

        view.setOnClickListener(this);
    }

    public void bindJuego(Juego juego) {
        this.juego = juego;

        nombreJuego.setText(juego.getTipoDeJuego());
        barDelJuego.setText(String.format("En %s", juego.getNombreBar()));
        setImagen(juego.getTipoDeJuego());

    }

    private void setImagen(String tipoDeJuego) {
        switch (tipoDeJuego) {
            case "Desafio":
                imagenDelJuego.setImageResource(R.drawable.placeholder); //TODO: imagenes para cada juego
                break;
        }
    }


    @Override
    public void onClick(View v) {
        clickListener.onClickJuego(juego);
    }
}
