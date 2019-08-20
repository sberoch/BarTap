package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Juego;


public class JuegoDelBarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Juego juego;
    private Context context;

    private ImageView imagenDelJuego;
    private TextView nombreJuego;
    private TextView resumenJuego;

    public interface OnJuegoHolderClickListener {
        void onClickJuego(Juego juego);
    }

    private OnJuegoHolderClickListener clickListener;

    public JuegoDelBarHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;

        imagenDelJuego = view.findViewById(R.id.imagen_juego);
        nombreJuego = view.findViewById(R.id.nombre_juego);
        resumenJuego = view.findViewById(R.id.resumen_juego);

        try {
            clickListener = (OnJuegoHolderClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickListener.onClickJuego(juego);
    }

    public void bindJuego(Juego juego) {
        this.juego = juego;

        setImagen(juego.getTipoDeJuego());
        nombreJuego.setText(juego.getTipoDeJuego());
        resumenJuego.setText(juego.getTextoResumen());
    }

    private void setImagen(String tipoDeJuego) {
        switch (tipoDeJuego) {
            case "Desafio":
                imagenDelJuego.setImageResource(R.drawable.logodesafio);
                break;
            case "Trivia":
                imagenDelJuego.setImageResource(R.drawable.logotrivia);
                break;
            case "Sorteo":
                imagenDelJuego.setImageResource(R.drawable.logosorteo);
                break;
        }
    }
}
