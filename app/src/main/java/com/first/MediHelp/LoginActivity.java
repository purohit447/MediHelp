package com.first.MediHelp;


import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.check_internet.Utility.networkChangeListner;
import com.first.MediHelp.Models.Patient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// it is login activity
// code
public class LoginActivity extends AppCompatActivity {
    private TextView signUpButton, forgotButton;
    private Button loginBtn, temp ;
    private TextInputEditText email,password;
    networkChangeListner network_change =  new networkChangeListner();
    private FirebaseDatabase db;
    private boolean isLoggedIn = false;
    private boolean isDoctor = false;
    private SharedPreferences sharedPreferences;
    private DatabaseReference ref;
    private String mail = "";
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

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        isLoggedIn = sharedPref.getBoolean("isLoggedIn" , false);
        mail = sharedPref.getString("id","");
        isDoctor = sharedPref.getBoolean("isDoctor" , false);
        if(isLoggedIn){
            saveDataAndMoveToNextActivity(isLoggedIn , mail , isDoctor);
        }
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users/Patients/");
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
                Toast.makeText(LoginActivity.this, "Temporary unavailable!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this , PatientActivity.class));
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
                ref = db.getReference("Users/Patients/");
               // Toast.makeText(LoginActivity.this, loginId + " " + logPassword, Toast.LENGTH_SHORT).show();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Patient p = dataSnapshot.getValue(Patient.class);
                            if(p != null){
                                if(p.getEmail().equals(loginId) && p.getPass().equals(logPassword)){
                                    Toast.makeText(LoginActivity.this, "Patient Login Succesfull ", Toast.LENGTH_SHORT).show();
                                    isLoggedIn = true;
                                    isDoctor = false;
                                    saveDataAndMoveToNextActivity(isLoggedIn , loginId , isDoctor);
                                    break;
                                }
                            }
                        }
                        if(!isLoggedIn){
                            ref = db.getReference("Users/Doctors/");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        Patient p = dataSnapshot.getValue(Patient.class);
                                        if(p != null){
                                            if(p.getEmail().equals(loginId) && p.getPass().equals(logPassword)){
                                                Toast.makeText(LoginActivity.this, "Doctor Login Succesfull ", Toast.LENGTH_SHORT).show();
                                                isLoggedIn = true;
                                                isDoctor  = true;
                                                saveDataAndMoveToNextActivity(isLoggedIn , loginId , isDoctor);
                                                break;
                                            }
                                        }
                                    }
                                    checkLogin(isLoggedIn , loginId);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginActivity.this, "Error -> " + error.toString() + " " + error.getDetails(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "DB Eror " + error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }
    private void checkLogin(boolean isLoggedIn , String loginId){
        if(!isLoggedIn) {
            Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
        }

    }private void saveDataAndMoveToNextActivity(boolean isLoggedIn , String loginid , boolean isDoctor){
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn" , isLoggedIn);
        editor.putBoolean("isDoctor" , isDoctor);
        editor.putString("id" , loginid);
        editor.commit(); // apply() can't be used due it's asynchronised property
        if(isDoctor){
            startActivity(new Intent(LoginActivity.this , DoctorActivity.class));
        }else{
            startActivity(new Intent(LoginActivity.this , PatientActivity.class));
        }
    }


}