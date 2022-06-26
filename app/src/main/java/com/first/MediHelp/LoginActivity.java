package com.first.MediHelp;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.check_internet.Utility.networkChangeListner;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity {
    private TextView signUpButton, forgotButton;
    private Button loginBtn, temp ;
    private TextInputEditText email,password;
    networkChangeListner network_change =  new networkChangeListner();

     //network validation
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(network_change, filter);
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(network_change);
        super.onStop();
    }

    // Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();               // hide action bar

        // login to signup

        signUpButton = findViewById(R.id.signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , SignupActivity.class));
            }
        });

        // forgot password
        forgotButton = findViewById(R.id.forgot);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "DataBase lagaya ni na vro!", Toast.LENGTH_SHORT).show();
            }
        });

        // temp entry

        temp = findViewById(R.id.temp);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PatientActivity.class));
                Toast.makeText(LoginActivity.this, "DataBase lagaya ni na vro!", Toast.LENGTH_SHORT).show();
            }
        });

        //  login variables for firebase
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        // login firebase check
        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String loginId = email.getText().toString().trim();
                final String logPassword = password.getText().toString().trim();

                if(loginId.isEmpty())
                {
                    email.setError("field required!");
                    return;
                }
                if(logPassword.isEmpty())
                {
                    password.setError("field required!");
                    return;
                }
                Toast.makeText(LoginActivity.this, "DataBase lagaya ni na vro!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}