package com.example.covid_19healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class HomeTeasting extends AppCompatActivity {
EditText name;
     TextView lat,lng,id;
double latitude,longitude;
String Name,ID;
    covidLoc covidLoc;
    int res=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teasting);
        name=findViewById(R.id.dename);
        lat=findViewById(R.id.lat);
        lng=findViewById(R.id.lng);
        id=findViewById(R.id.eid);

        covidloc();
    }
    public void covidloc() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //Toast.makeText(getApplicationContext(),Double.toString(latitude)+"     "+Double.toString(longitude),Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(HomeTeasting.this).requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        LocationServices.getFusedLocationProviderClient(HomeTeasting.this).removeLocationUpdates(this);
                        if(locationResult!=null && locationResult.getLocations().size()>0){
                            int latestlocindex=locationResult.getLocations().size()-1;
                            latitude=locationResult.getLocations().get(latestlocindex).getLatitude();
                            longitude=locationResult.getLocations().get(latestlocindex).getLongitude();
                           lat.setText("Latitude :-"+Double.toString(latitude));
                                lng.setText("Longitude :-"+Double.toString(longitude));
                          /*  Name=name.getText().toString();
                            ID=id.getText().toString();
                                covidLoc = new covidLoc(latitude, longitude,Name,ID);
                            FirebaseDatabase.getInstance().getReference().child("loc").push().setValue(covidLoc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"loc saved",Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(getApplicationContext(),"loc not saved",Toast.LENGTH_LONG).show();
                                    }


                                }
                            });



                          */
                        }
                        Toast.makeText(getApplicationContext(),Double.toString(latitude)+"/l"+Double.toString(longitude),Toast.LENGTH_LONG).show();
                    }

                }
                , Looper.myLooper());

    }

    public void update(View view) {
        Name=name.getText().toString();
       // ID=id.getText().toString();
        covidLoc = new covidLoc(latitude, longitude,Name,id.getText().toString(),res);
        FirebaseDatabase.getInstance().getReference().child("loc").push().setValue(covidLoc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"loc saved",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"loc not saved",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}