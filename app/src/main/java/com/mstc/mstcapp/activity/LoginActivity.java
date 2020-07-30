package com.mstc.mstcapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mstc.mstcapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView loginForgotPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        findViewById();
        firebaseAuth = FirebaseAuth.getInstance();
        onClickListener();
    }

    private void findViewById()
    {
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        loginForgotPassword = findViewById(R.id.loginForgotPassword);
    }

    private void onClickListener()
    {
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if(checkEmpty())
                {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent i= new Intent(LoginActivity.this,NavActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                            }
                            else
                            {
                                Snackbar.make(findViewById(android.R.id.content),"Unable To Login!!!",Snackbar.LENGTH_SHORT).setTextColor(getColor(R.color.colorPrimary)).setBackgroundTint(getColor(R.color.white)).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Snackbar.make(findViewById(android.R.id.content),"Unable To Login!!!",Snackbar.LENGTH_SHORT).setTextColor(getColor(R.color.colorPrimary)).setBackgroundTint(getColor(R.color.white)).show();
                        }
                    });
                }
            }
        });
    }

    public Boolean checkEmpty()
    {
        if(loginEmail.getText().length()==0)
        {
            loginEmail.setError("Please enter your email");
            loginEmail.requestFocus();
            return false;
        }
        else if(loginPassword.getText().length()==0)
        {
            loginPassword.setError("Please enter your password");
            loginPassword.requestFocus();
            return false;
        }
        return true;
    }
    
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginActivity.this,StartActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}