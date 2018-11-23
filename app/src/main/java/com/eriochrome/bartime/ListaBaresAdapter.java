package com.eriochrome.bartime;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;

class ListaBaresAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Bar> bares;

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
        estrellas.setText(String.valueOf(bar.getEstrellas()));

        ImageView imagenBar = view.findViewById(R.id.imagen_bar);
        //TODO: mock
        //TODO: se queda sin memoria porque las imagenes son grandes
        switch (bar.getNumeroDeFoto()) {
            case 1:
                imagenBar.setImageResource(R.drawable.testbar);
                break;
            case 2:
                imagenBar.setImageResource(R.drawable.testbar2);
                break;
            case 3:
                imagenBar.setImageResource(R.drawable.testbar3);
                break;
            case 4:
                imagenBar.setImageResource(R.drawable.testbar4);
                break;
            case 5:
                imagenBar.setImageResource(R.drawable.testbar5);
                break;
        }


        return view;
    }
}
