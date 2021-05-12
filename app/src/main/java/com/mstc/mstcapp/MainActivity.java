package com.mstc.mstcapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mstc.mstcapp.util.Constants;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static boolean isAppRunning = false;
    int exitCount = 0;
    private Context context = this;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private int[] ids = {R.id.home, R.id.resources, R.id.explore};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        drawerLayout = findViewById(R.id.drawerLayout);



        drawerLayout.findViewById(R.id.instagram).setOnClickListener(v -> openURL(Constants.INSTAGRAM_URL));
        drawerLayout.findViewById(R.id.facebook).setOnClickListener(v -> openURL(Constants.FACEBOOK_URL));
        drawerLayout.findViewById(R.id.linkedin).setOnClickListener(v -> openURL(Constants.LINKEDIN_URL));
        drawerLayout.findViewById(R.id.github).setOnClickListener(v -> openURL(Constants.GITHUB_URL));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();
        toolbar.setNavigationIcon(ContextCompat.getDrawable(context, R.drawable.ic_navigation));

        findViewById(R.id.home).setOnClickListener(v -> {
            selectTab(ids[0]);
            navController.popBackStack();
            navController.navigate(R.id.navigation_home);
        });

        findViewById(R.id.resources).setOnClickListener(v -> {
            selectTab(ids[1]);
            navController.popBackStack();
            navController.navigate(R.id.navigation_resources);
        });

        findViewById(R.id.explore).setOnClickListener(v -> {
            selectTab(ids[2]);
            navController.popBackStack();
            navController.navigate(R.id.navigation_explore);
        });
    }

    public void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else if (navController.getCurrentDestination().getId() != R.id.navigation_home) {
            selectTab(ids[0]);
            navController.popBackStack();
            navController.navigate(R.id.navigation_home);
        } else {
            exitCount++;
            View view = findViewById(android.R.id.content);
            Snackbar.make(context, view, "Press back again to exit", BaseTransientBottomBar.LENGTH_SHORT)
                    .addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            exitCount = 0;
                        }
                    }).show();
            if (exitCount == 2) {
                super.onBackPressed();
            }
        }
    }

    private void selectTab(int id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] != id) setUnselected(ids[i]);
        }
        setSelected(id);
    }

    private void setUnselected(int id) {
        Chip chip = findViewById(id);
        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
        chip.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
        chip.setChipIconTint(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.textColorPrimary)));
    }

    private void setSelected(int id) {
        Chip chip = findViewById(id);
        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorTertiaryBlue)));
        chip.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        chip.setChipIconTint(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary)));
    }
}