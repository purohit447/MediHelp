package com.first.MediHelp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.check_internet.Utility.networkChangeListner;
import com.first.MediHelp.Models.Patient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    private TextView loginBtn;
    private Button signupBtn;
    private TextInputEditText fullname,email,phone,password;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ProgressDialog loader;
    private RadioButton patientBtn , doctorBtn;
    networkChangeListner network_change =  new networkChangeListner();

    // network validation
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
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();  // action bar hide

        doctorBtn = findViewById(R.id.doctorReg);
        patientBtn = findViewById(R.id.patientReg);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users/");
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this , LoginActivity.class));
            }
        });

        //field information for firebase
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.signupEmail);
        phone = findViewById(R.id.SignupMobile);
        password = findViewById(R.id.signupPassword);

        //button to signUP
        signupBtn = findViewById(R.id.signupButton);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // signUp variables
            final String strfullname = fullname.getText().toString().trim();
            final String stremail = email.getText().toString().trim();
            final String strphone = phone.getText().toString().trim();
            final String strpassword = password.getText().toString().trim();
            final boolean isDoctor = doctorBtn.isChecked();

            // field validation
            if(strfullname.isEmpty()){
                fullname.setError("field required!");
                return;
            }
            if(stremail.isEmpty()) {
                email.setError("field required!");
                return;
            }
            if(strphone.isEmpty()) {
                phone.setError("field required!");
                return;
            }
            if(strphone.length()!=10) {
                phone.setError("invalid number!");
                return;
            }
            if(strpassword.length()<8)
            {
                password.setError("password is too short!");
                return;
            }
                Patient pt = new Patient(strfullname , stremail , strphone , strpassword);
            if(!isDoctor){

                ref.child("Patients/"+strphone+"/").setValue(pt);


            }else{
                ref.child("Doctors/"+strphone+"/").setValue(pt);
            }
            Toast.makeText(SignupActivity.this, "SignUp Successfull!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivity.this , LoginActivity.class));
            }
        });


    }
}