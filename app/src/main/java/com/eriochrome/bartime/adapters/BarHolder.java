package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eriochrome.bartime.vistas.PaginaBarActivity;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.utils.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private Bar bar;
    private View view;

    private TextView nombreBar;
    private TextView ubicacionBar;
    private TextView estrellas;
    private ImageView imagenBar;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private static final int MIN_ESTRELLAS = 2;

    public BarHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.view = view;

        this.nombreBar = view.findViewById(R.id.nombre_bar);
        this.ubicacionBar = view.findViewById(R.id.ubicacion_bar);
        this.estrellas = view.findViewById(R.id.estrellas);
        this.imagenBar = view.findViewById(R.id.imagen_bar);

        view.setOnClickListener(this);
    }


    public void bindBar(Bar bar) {
        Typeface tfLight = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
        Typeface tfBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");

        this.bar = bar;

        this.nombreBar.setText(bar.getNombre());
        this.nombreBar.setTypeface(tfBold);

        this.ubicacionBar.setText(bar.getUbicacion());
        this.ubicacionBar.setTypeface(tfLight);

        setEstrellas();

        String nombreBar = bar.getNombre().replaceAll(" ", "_");
        String imagePath = nombreBar + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(imagePath);
        GlideApp.with(this.view)
                .load(imagenRef).placeholder(R.drawable.placeholder)
                .into(this.imagenBar);
    }


    private void setEstrellas() {
        //Para que el bar no quede mal, solo se muestran las estrellas si tiene una puntuacion
        // mayor a la minima.
        double estrellasDelBar = bar.getEstrellas();
        if (estrellasDelBar >= MIN_ESTRELLAS) {
            this.estrellas.setText(String.format("%.1f", estrellasDelBar));
        } else {
            this.estrellas.setText(" -- ");
        }
    }


    @Override
    public void onClick(View v) {
        if(this.bar != null) {
            Intent intent = new Intent(context, PaginaBarActivity.class);
            intent.putExtra("bar", bar);
            context.startActivity(intent);
        }
    }
}
