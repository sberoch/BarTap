package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eriochrome.bartime.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroduccionBarActivity extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        addSlide(AppIntroFragment.newInstance(res.getString(R.string.intro_b1_tit), res.getString(R.string.intro_b1_des), R.drawable.intro1, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance(res.getString(R.string.intro_b2_tit), res.getString(R.string.intro_b2_des), R.drawable.intro3, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(res.getString(R.string.intro_b3_tit), res.getString(R.string.intro_b3_des), R.drawable.intro5, getResources().getColor(R.color.colorAccent)));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, BarControlActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, BarControlActivity.class));
        finish();
    }

}
