package com.example.covid_19healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText Email;
    Button login;
    Button sineup;
    EditText Password;

    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        Password = findViewById(R.id.epass);
        login = findViewById(R.id.btn1);
        sineup = findViewById(R.id.btn2);
        progressDialog=new ProgressDialog(this);
        sineup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,DoctorRegistatiion.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    progressDialog.setTitle("Loggin In");
                    progressDialog.setMessage("Please wait! While your Account is Logging In");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    login(email,password);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Please Enter Email & Password",Toast.LENGTH_LONG).show();
                }
            }
        });

    } private void login(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Entered Email & Password is wrong",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void doctorReg(View view) {
    }
}