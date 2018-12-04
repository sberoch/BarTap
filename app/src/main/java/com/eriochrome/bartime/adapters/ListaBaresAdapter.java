package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eriochrome.bartime.BarActivity;
import com.eriochrome.bartime.ListadoBares;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.utils.GlideApp;
import com.eriochrome.bartime.utils.MyAppGlideModule;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListaBaresAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bar> bares;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public ListaBaresAdapter(ArrayList<Bar> listaBares, Context context) {
        bares = listaBares;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bares.size();
    }

    @Override
    public Object getItem(int i) {
        return bares.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_bar, viewGroup, false);
        }

        Typeface tfLight = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
        Typeface tfBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");

        Bar bar = bares.get(i);

        TextView nombreBar = view.findViewById(R.id.nombre_bar);
        nombreBar.setText(bar.getNombre());
        nombreBar.setTypeface(tfBold);

        TextView descripcionBar = view.findViewById(R.id.desc_bar);
        descripcionBar.setText(bar.getDescripcion());
        descripcionBar.setTypeface(tfLight);

        TextView estrellas = view.findViewById(R.id.estrellas);
        estrellas.setText(String.format("%.1f",bar.getEstrellas()));

        ImageView imagenBar = view.findViewById(R.id.imagen_bar);
        String imagePath = bar.getNombre() + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(imagePath);
        //TODO: se ve como el orto el placeholder
        GlideApp.with(view)
                .load(imagenRef).placeholder(R.drawable.placeholder)
                .into(imagenBar);

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, BarActivity.class);
            intent.putExtra("bar", bar);
            context.startActivity(intent);
        });

        return view;
    }
}
