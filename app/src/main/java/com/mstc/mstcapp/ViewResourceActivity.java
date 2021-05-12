package com.mstc.mstcapp;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.mstc.mstcapp.ui.resources.ViewPagerAdapter;

public class ViewResourceActivity extends AppCompatActivity {
    private static final String TAG = "ViewResourceActivity";
    private Context context = this;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String domain;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        domain = getIntent().getStringExtra("domain");
        int position = getIntent().getIntExtra("position", 0);

        setTheme(getToolbarColor(position));

        setContentView(R.layout.activity_view_resource);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(context, R.drawable.ic_back));

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(domain.toUpperCase());

        TextView toolbar_description = findViewById(R.id.toolbar_description);
        toolbar_description.setText("The best and trusted resources for you to get started");

        ImageView toolbar_image = findViewById(R.id.toolbar_image);
        toolbar_image.setImageDrawable(ContextCompat.getDrawable(context, getImage()));

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), domain);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(domain.toUpperCase());

    }

    private int getImage() {
        if (domain.equalsIgnoreCase("android")) return R.drawable.ic_app_dev;
        if (domain.equalsIgnoreCase("frontend")) return R.drawable.ic_frontend;
        if (domain.equalsIgnoreCase("backend")) return R.drawable.ic_backend;
        if (domain.equalsIgnoreCase("design")) return R.drawable.ic_design;
        if (domain.equalsIgnoreCase("machine learning")) return R.drawable.ic_app_dev;
        if (domain.equalsIgnoreCase("competitive coding")) return R.drawable.ic_cc;
        return 0;
    }

    private int getToolbarColor(int position) {
        if (position % 3 == 0)
            return R.style.resources_red;
        else if (position % 3 == 1)
            return R.style.resources_blue;
        else
            return R.style.resources_yellow;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}