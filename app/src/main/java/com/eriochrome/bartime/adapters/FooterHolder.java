package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.eriochrome.bartime.vistas.AgregarBarUsuarioActivity;


public class FooterHolder extends RecyclerView.ViewHolder {

    public FooterHolder(Context context, @NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, AgregarBarUsuarioActivity.class);
            context.startActivity(i);
        });
    }
}
