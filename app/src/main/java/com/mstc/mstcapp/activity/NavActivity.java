package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.fragments.ExclusiveFragment;
import com.mstc.mstcapp.fragments.FeedFragment;
import com.mstc.mstcapp.fragments.HighlightFragment;
import com.mstc.mstcapp.fragments.InformationFragment;
import com.mstc.mstcapp.fragments.ResourcesFragment;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.model.highlights.GithubObject;
import com.mstc.mstcapp.model.highlights.ProjectsObject;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity {

    private TextView appBarTitle;
    private ConstraintLayout appBar;
    private CircularImageView appBarProfilePicture;
    private ImageView stcLogo;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

    private int backButtonCount = 0;

    //Feed Fragment
    public static ArrayList<FeedObject> feedList = new ArrayList<>();
    public static boolean sharedFeed=false;
    public static boolean loadData=true;

    //Project Fragment
    public static ArrayList<ProjectsObject> projectList = new ArrayList<>();

    //Github Fragment
    public static ArrayList<GithubObject> githubList = new ArrayList<>();

    //Events Fragment
    public static ArrayList<EventObject> eventList = new ArrayList<>();

    //Fragments
    private Fragment activeFragment;
    private FeedFragment feedFragment=new FeedFragment();
    private ResourcesFragment resourcesFragment =new ResourcesFragment();
    private HighlightFragment highlightFragment =new HighlightFragment();
    private ExclusiveFragment exclusiveFragment =new ExclusiveFragment();
    private InformationFragment informationFragment=new InformationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById();
        onClickListener();
        profilePicture();
        bottomNavigation();

        Intent intent = getIntent();
        boolean resource = intent.getBooleanExtra("Resource", false);
        if(resource)
        {
            bottomNavigationView.setSelectedItemId(R.id.nav_resources);
        }

    }

    private void findViewById(){
        appBarProfilePicture=findViewById(R.id.appBarProfilePicture);
        stcLogo=findViewById(R.id.stcLogo);
        appBarTitle = findViewById(R.id.appBarTitle);
        appBar = findViewById(R.id.appBar);
        bottomNavigationView = findViewById(R.id.nav_view);
    }

    private void onClickListener(){

        appBarProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        stcLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser()==null){
                    new AlertDialog.Builder(NavActivity.this)
                            .setMessage("Are you STC-VIT Member?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor tempEditor = getSharedPreferences(getPackageName(),MODE_PRIVATE).edit();
                                    tempEditor.putBoolean(getPackageName(),false);
                                    tempEditor.apply();
                                    Intent intent = new Intent(NavActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }

    private void profilePicture(){
        if(firebaseAuth.getCurrentUser()==null)
        {
            bottomNavigationView.getMenu().removeItem(R.id.nav_exclusive);
            appBarProfilePicture.setVisibility(View.INVISIBLE);
        }
        else
        {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String email = user.getEmail();

            assert email != null;
            String userEmail = email.replace('.', '_');
            StorageReference storeRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(userEmail);
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
                    Snackbar.make(findViewById(android.R.id.content),"Unable to download picture.",Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(getColor(R.color.colorPrimary)).setTextColor(getColor(R.color.permWhite)).show();
                }
            });
        }
    }

    private void bottomNavigation(){
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,informationFragment).hide(informationFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,exclusiveFragment).hide(exclusiveFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,highlightFragment).hide(highlightFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,resourcesFragment).hide(resourcesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,feedFragment).commit();
        activeFragment=feedFragment;
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        appBar.setElevation(4.0f);
        appBarTitle.setText("HOME");


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item)
            {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Fragment selectedFragment= feedFragment;
                        backButtonCount = 0;
                        switch (item.getItemId()){
                            case R.id.nav_home:
                                appBar.setElevation(4.0f);
                                appBarTitle.setText("HOME");
                                selectedFragment = feedFragment;
                                break;
                            case R.id.nav_resources:
                                selectedFragment = resourcesFragment;
                                appBar.setElevation(4.0f);
                                appBarTitle.setText("RESOURCES");
                                break;
                            case R.id.nav_archive:
                                selectedFragment = highlightFragment;
                                appBar.setElevation(0.0f);
                                appBarTitle.setText("HIGHLIGHTS");
                                break;
                            case R.id.nav_exclusive:
                                selectedFragment = exclusiveFragment;
                                appBar.setElevation(0.0f);
                                appBarTitle.setText("EXCLUSIVE");
                                break;
                            case R.id.nav_info:
                                selectedFragment = informationFragment;
                                appBar.setElevation(4.0f);
                                appBarTitle.setText("INFORMATION");
                                break;
                        }
                        if(selectedFragment!=activeFragment){
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .show(selectedFragment)
                                    .hide(activeFragment)
                                    .commit();
                            activeFragment=selectedFragment;
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Intent intent = new Intent(NavActivity.this,NavActivity.class);
        startActivity(intent);
        super.onConfigurationChanged(newConfig);
    }

}