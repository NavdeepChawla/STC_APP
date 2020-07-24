package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.FeedAdapter;
import com.mstc.mstcapp.adapter.highlights.EventAdapter;
import com.mstc.mstcapp.fragments.ExclusiveFragment;
import com.mstc.mstcapp.fragments.FeedFragment;
import com.mstc.mstcapp.fragments.HighlightFragment;
import com.mstc.mstcapp.fragments.InformationFragment;
import com.mstc.mstcapp.fragments.ResourcesFragment;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.highlights.EventObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavActivity extends AppCompatActivity {

    public static TextView appBarTitle;
    public static ConstraintLayout appBar;
    public static CircularImageView appBarProfilePicture;
    public static ImageView stcLogo;
    private static StorageReference storeRef;
    private static String email, userEmail;
    private static FirebaseUser user;
    private int backButtonCount = 0;
    private int prevPage=0;
    private int currentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        appBarProfilePicture=findViewById(R.id.appBarProfilePicture);
        stcLogo=findViewById(R.id.stcLogo);
        appBarTitle = findViewById(R.id.appBarTitle);
        appBar = findViewById(R.id.appBar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null)
        {
            bottomNavigationView.getMenu().removeItem(R.id.nav_exclusive);
            appBarProfilePicture.setVisibility(View.INVISIBLE);
            stcLogo.setEnabled(false);
        }
        else
        {
            user=firebaseAuth.getCurrentUser();
            email =user.getEmail();

            assert email != null;
            userEmail = email.replace('.','_');
            storeRef= FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(userEmail);
            storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    stcLogo.setVisibility(View.INVISIBLE);
                    appBarProfilePicture.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(uri).into(appBarProfilePicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(findViewById(android.R.id.content),"Unable to download picture.",Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(getColor(R.color.colorPrimary)).setActionTextColor(getColor(R.color.white)).show();
                }
            });
        }
        Log.i("RETROFIT :","Starting");


        //Intent To Profile Activity
        appBarProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        Intent intent = getIntent();
        boolean resource = intent.getBooleanExtra("Resource",false);
        if(resource)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new ResourcesFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_resources);
        }
        else
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new FeedFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item)
            {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Fragment selectedFragment= new FeedFragment();
                                backButtonCount = 0;
                                switch (item.getItemId()){
                                    case R.id.nav_home:
                                        selectedFragment = new FeedFragment();
                                        currentPage=0;
                                        break;
                                    case R.id.nav_resources:
                                        selectedFragment = new ResourcesFragment();
                                        currentPage=1;
                                        break;
                                    case R.id.nav_archive:
                                        selectedFragment = new HighlightFragment();
                                        currentPage=2;
                                        break;
                                    case R.id.nav_exclusive:
                                        selectedFragment = new ExclusiveFragment();
                                        currentPage=3;
                                        break;
                                    case R.id.nav_info:
                                        selectedFragment = new InformationFragment();
                                        currentPage=4;
                                        break;
                                }

                                if(prevPage<currentPage)
                                {
                                    prevPage=currentPage;
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                                            .replace(R.id.nav_host_fragment, selectedFragment)
                                            .commit();
                                }
                                else if(currentPage<prevPage)
                                {
                                    prevPage=currentPage;
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                                            .replace(R.id.nav_host_fragment, selectedFragment)
                                            .commit();
                                }

                            }
                        });
                return true;
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
            Snackbar.make(findViewById(android.R.id.content),"Press back again to exit.",Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(getColor(R.color.colorPrimary)).setActionTextColor(getColor(R.color.white)).show();
            backButtonCount++;
        }
    }
}