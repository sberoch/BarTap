package com.eriochrome.bartime.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.eriochrome.bartime.R;

public class MySliderView extends BaseSliderView {

    public MySliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_slider_view,null);
        ImageView target = v.findViewById(R.id.daimajia_slider_image);
        target.setClipToOutline(true);
        bindEventAndShow(v, target);
        return v;
    }
}
