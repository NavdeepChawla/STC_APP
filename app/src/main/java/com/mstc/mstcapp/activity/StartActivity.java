package com.mstc.mstcapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mstc.mstcapp.R;

public class StartActivity extends AppCompatActivity {

    private Button startSTC;
    private Button startNonSTC;
    private int backButtonCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if((firebaseAuth.getCurrentUser()!=null)||getSharedPreferences(getPackageName(),MODE_PRIVATE).getBoolean(getPackageName(),false))
        {
            Intent intent=new Intent(StartActivity.this,NavActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }

        findViewById();
        setAnimation();
        onClickListener();
    }

    private void findViewById()
    {
        startNonSTC = findViewById(R.id.startNonSTCMember);
        startSTC = findViewById(R.id.startSTCMember);
    }

    private void setAnimation()
    {
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        //fadeIn.setStartOffset(2000);
        startSTC.setAnimation(fadeIn);
        startNonSTC.setAnimation(fadeIn);
    }

    private void onClickListener()
    {
        startSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        startNonSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor tempEditor = getSharedPreferences(getPackageName(),MODE_PRIVATE).edit();
                tempEditor.putBoolean(getPackageName(),true);
                tempEditor.apply();
                Intent intent=new Intent(StartActivity.this,NavActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            backButtonCount = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content),"Press back again to exit.",Snackbar.LENGTH_SHORT).setTextColor(getColor(R.color.colorPrimary)).setBackgroundTint(getColor(R.color.permWhite)).show();
            backButtonCount++;
        }
    }

}