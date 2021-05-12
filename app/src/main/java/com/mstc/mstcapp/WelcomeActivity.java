package com.mstc.mstcapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mstc.mstcapp.util.Constants;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";
    SharedPreferences.Editor editor;
    private Context context = this;
    private SharedPreferences sharedPreferences;
    private int NoOfSlides = 3, position = 0;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private ImageView imageView;
    private TextView textView;
    private int[] images = {R.drawable.ic_onboarding_1, R.drawable.ic_onboarding_2, R.drawable.ic_onboarding_3};
    private int[] texts = {R.string.onboarding1, R.string.onboarding2, R.string.onboarding3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = context.getSharedPreferences(Constants.STC_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dotsLayout = findViewById(R.id.layoutDots);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        addBottomDots(position);
        findViewById(R.id.next).setOnClickListener(v -> {
            position++;
            if (position < NoOfSlides) {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, images[position]));
                textView.setText(getString(texts[position]));
                addBottomDots(position);
            } else {
                editor.putBoolean("isFirstLaunch", false);
                editor.apply();
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[NoOfSlides];
        int colorsActive = ContextCompat.getColor(context, R.color.colorPrimary);
        int colorsInactive = ContextCompat.getColor(context, R.color.gray);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&nbsp;&#8226;&nbsp"));
            dots[i].setTextSize(25);
            dots[i].setTextColor(colorsInactive);
            dots[i].setPadding(15, 15, 15, 15);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive);
            dots[currentPage].setTextSize(40);

        }
    }
}