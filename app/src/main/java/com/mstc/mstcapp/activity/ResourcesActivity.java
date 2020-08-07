package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resources.ViewPagerResourcesAdapter;
import com.mstc.mstcapp.fragments.HighlightFragment;
import com.mstc.mstcapp.fragments.resources.ArticleLinksFragment;
import com.mstc.mstcapp.fragments.resources.ResourcesFolderFragment;
import com.mstc.mstcapp.fragments.resources.RoadmapFragment;

public class ResourcesActivity extends AppCompatActivity {

    private CircularImageView res_appBarProfilePicture;
    private ImageView resourcesStcLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        resourcesStcLogo=findViewById(R.id.resStcLogo);
        res_appBarProfilePicture=findViewById(R.id.res_appBarProfilePicture);
        TextView resappbarTitle = findViewById(R.id.resappBarTitle);
        TabLayout resourcesTablayout = findViewById(R.id.tab_view_resources);
        ViewPager resourcesViewPager = findViewById(R.id.viewpager_res);

        Intent i =getIntent();
        String domain = i.getStringExtra("domain");
        if(domain!=null)
        {
            resappbarTitle.setText(domain.toUpperCase());
        }


        ViewPagerResourcesAdapter adapter=new ViewPagerResourcesAdapter(getSupportFragmentManager());
        if(domain!=null)
        {
            adapter.addFragment(new RoadmapFragment(domain.toLowerCase()),"Roadmap");
            adapter.addFragment(new ResourcesFolderFragment(domain.toLowerCase()),"Resources");
            adapter.addFragment(new ArticleLinksFragment(domain.toLowerCase()),"Articles");
            resourcesViewPager.setOffscreenPageLimit(3);
        }

        resourcesViewPager.setAdapter(adapter);
        resourcesTablayout.setupWithViewPager(resourcesViewPager);
        resourcesViewPager.setPageTransformer(true, new HighlightFragment.DepthPageTransformer());
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String email = user.getEmail();

            assert email != null;
            String userEmail = email.replace('.', '_');
            StorageReference storeRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(userEmail);
            storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    resourcesStcLogo.setVisibility(View.INVISIBLE);
                    res_appBarProfilePicture.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(uri).into(res_appBarProfilePicture);
                    res_appBarProfilePicture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to download picture.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);


            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ResourcesActivity.this,NavActivity.class);
        intent.putExtra("Resource",true);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Intent intent = new Intent(ResourcesActivity.this,NavActivity.class);
        startActivity(intent);
        super.onConfigurationChanged(newConfig);
    }
}