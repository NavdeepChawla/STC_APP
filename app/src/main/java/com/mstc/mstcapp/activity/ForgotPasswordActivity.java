package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mstc.mstcapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button buttonReset;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout resetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_forgot_password);

        resetEmail =findViewById(R.id.resetEmail);
        buttonReset =findViewById(R.id.resetButton);
        resetLayout =findViewById(R.id.resetLayout);
        firebaseAuth =FirebaseAuth.getInstance();

        //USER ALREADY LOGGED IN
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FETCHING EMAIL FROM THE EDIT TEXT
                String email = resetEmail.getText().toString().trim();
                if(email!=null && !email.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(Void aVoid) {
                            final Snackbar snackbar = Snackbar.make(resetLayout, "Reset Link sent to your email!", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Okay", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.setActionTextColor(Color.WHITE);
                            snackbar.show();

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final Snackbar snackbar = Snackbar.make(resetLayout, "Unable to send reset link to your email", Snackbar.LENGTH_INDEFINITE);
                        }
                    });

                }
                //WHEN EMAIL NOT ENTERED
                else{
                    Toast.makeText(getApplicationContext(),"Email must be entered.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}




