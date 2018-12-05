package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eriochrome.bartime.AgregarBarUsuario;


public class FooterHolder extends RecyclerView.ViewHolder {

    public FooterHolder(Context context, @NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, AgregarBarUsuario.class);
            context.startActivity(i);
        });
    }
}
