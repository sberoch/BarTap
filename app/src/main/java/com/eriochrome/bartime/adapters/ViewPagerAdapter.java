package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.utils.GlideApp;
import com.eriochrome.bartime.utils.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private StorageReference storageReference;
    private Bar bar;


    public ViewPagerAdapter(Context context, Bar bar) {
        this.bar = bar;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return bar.getCantidadDeFotos();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_imagen_viewpager, container, false);
        ImageView imageView = layout.findViewById(R.id.imageView);

        String path = bar.getNombre() + Utils.getNumeroDeFoto(position) + ".jpg";
        StorageReference imageRef = storageReference.child("imagenes").child(path);

        GlideApp.with(layout)
                .load(imageRef)
                .into(imageView);

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
