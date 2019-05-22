package com.eriochrome.bartime;

import android.app.Application;

import com.eriochrome.bartime.utils.TypefaceUtil;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Lato-Bold.ttf");
    }
}
