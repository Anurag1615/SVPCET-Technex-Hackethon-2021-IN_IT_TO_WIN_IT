package com.example.covid_19healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorRegistatiion extends AppCompatActivity {
    EditText name, phone, pass, add, mail, exp, edu, spec;
    Button register;
    //Firebase Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Database Reference
    private DatabaseReference mUserDetails = FirebaseDatabase.getInstance().getReference();

    private Toolbar mToolbar;
    private ProgressDialog mRegProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registatiion);
        mRegProgress = new ProgressDialog(this);
        name = findViewById(R.id.dename);
        phone = findViewById(R.id.depho);
        mail = findViewById(R.id.demail);
        pass = findViewById(R.id.depas);
        add = findViewById(R.id.deadd);
        exp = findViewById(R.id.dedit_doctor_experiance);
        edu = findViewById(R.id.dedit_doctor_education);
        spec = findViewById(R.id.dedit_doctor_specialization);
        register = findViewById(R.id.btn2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Experience = exp.getText().toString();
                //  String bloodgroup = mBloodGroup.getEditText().getText().toString();
                String contactnumber = phone.getText().toString();
                String address = add.getText().toString();
                String email = mail.getText().toString();
                String password = pass.getText().toString();
                String Specialization = spec.getText().toString();
                String Education = edu.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Education) && !TextUtils.isEmpty(contactnumber) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(Specialization)) {
                    mRegProgress.setTitle("Creating Account");
                    mRegProgress.setMessage("Please wait We are Prrocessing");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    createAccount(Name, Experience, Education, contactnumber, address, email, password, Specialization);

                }

            }
        });
    }

    private void createAccount(final String Name, final String experience, final String education, final String contactnumber, final String address, final String email, final String password, final String specialization) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DoctorRegistatiion.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    mUserDetails.child("User_Type").child(uid).child("Type").setValue("Doctor");

                    HashMap<String, String> doctorDetails = new HashMap<>();
                    doctorDetails.put("Name", Name);
                    doctorDetails.put("Experiance", experience);
                    doctorDetails.put("Education", education);
                    doctorDetails.put("Specialization", specialization);
                    doctorDetails.put("Contact", contactnumber);
                    doctorDetails.put("Address", address);
                    doctorDetails.put("Email", email);
                    doctorDetails.put("Password", password);
                    mUserDetails.child("Doctor_Details").child(uid).setValue(doctorDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mRegProgress.dismiss();
                            startActivity(new Intent(DoctorRegistatiion.this,HomeTeasting.class));
                            Toast.makeText(DoctorRegistatiion.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    mRegProgress.hide();
                    Toast.makeText(DoctorRegistatiion.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}