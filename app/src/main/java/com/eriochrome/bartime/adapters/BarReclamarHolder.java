package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.utils.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.recyclerview.widget.RecyclerView;

public class BarReclamarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private Bar bar;
    private View view;

    private TextView nombreBar;
    private TextView ubicacionBar;
    private ImageView imagenBar;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public interface Listener {
        void onClickBar(Bar bar);
    }

    private Listener listener;

    public BarReclamarHolder(View view, Context context) {
        super(view);
        this.context = context;
        this.listener = (Listener) context;
        this.view = view;

        this.nombreBar = view.findViewById(R.id.nombre_bar);
        this.ubicacionBar = view.findViewById(R.id.ubicacion_bar);
        this.imagenBar = view.findViewById(R.id.imagen_bar);

        view.setOnClickListener(this);
    }

    public void bind(Bar bar) {
        Typeface tfLight = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");

        this.bar = bar;
        this.nombreBar.setText(bar.getNombre());
        this.ubicacionBar.setText(bar.getUbicacion());
        this.ubicacionBar.setTypeface(tfLight);

        String nombreBar = bar.getNombre().replaceAll(" ", "_");
        String imagePath = nombreBar + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(imagePath);
        GlideApp.with(this.view)
                .load(imagenRef).placeholder(R.drawable.placeholder)
                .into(this.imagenBar);

    }

    @Override
    public void onClick(View v) {
        listener.onClickBar(bar);
    }
}
