package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mstc.mstcapp.R;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private CircularImageView profilePicture;
    private TextView profileName, profileEmail, profileRegNo, profileDomain, profileRoomNo, profilePhoneNo;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mProgressCircular;
    private RelativeLayout profileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProgressCircular=findViewById(R.id.progressBar);
        profilePicture =findViewById(R.id.appBarProfilePicture);
        profileName =findViewById(R.id.userName);
        profileEmail =findViewById(R.id.userEmail);
        profileRegNo =findViewById(R.id.userRegNo);
        Button buttonLogout = findViewById(R.id.resetButton);
        profileRoomNo =findViewById(R.id.room_user);
        profileDomain =findViewById(R.id.domain_user);
        profilePhoneNo =findViewById(R.id.phone_user);
        profileLayout =findViewById(R.id.rLayout);

        firebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();

        assert email != null;
        String userEmail = email.replace('.', '_');

        StorageReference storeRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(userEmail);

        //FETCHING PROFILE PIC FROM FIRESTORAGE AND PUTTING IT ON THE VIEW
        storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(profilePicture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Unable to download picture.", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(userEmail);


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent =new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("Check",true);
                startActivity(intent);
                finish();
            }
        });


        //FETCHING DATA FROM THE FIREBASE ONE BY ONE AND PUTTING IT ONTO THE VIEWS
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                profileName.setText(name);
                String uMail= Objects.requireNonNull(snapshot.child("userEmail").getValue()).toString();
                profileEmail.setText(uMail);
                String uRegNo= Objects.requireNonNull(snapshot.child("userRegNo").getValue()).toString();
                profileRegNo.setText(uRegNo);
                String uRoomNo= Objects.requireNonNull(snapshot.child("userRoom").getValue()).toString();
                profileRoomNo.setText(uRoomNo);
                String uPhone= Objects.requireNonNull(snapshot.child("userPhone").getValue()).toString();
                profilePhoneNo.setText(uPhone);
                String uDomain= Objects.requireNonNull(snapshot.child("userDomain").getValue()).toString();
                profileDomain.setText(uDomain);

                mProgressCircular.setVisibility(View.INVISIBLE);
                profileLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressCircular.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ProfileActivity.this,NavActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Intent intent = new Intent(ProfileActivity.this,NavActivity.class);
        startActivity(intent);
        super.onConfigurationChanged(newConfig);
    }

}