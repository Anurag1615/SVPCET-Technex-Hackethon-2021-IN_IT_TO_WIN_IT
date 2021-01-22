package com.example.covid_19healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView userName;
    TextView userEmail;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private TabLayout mTabLayout;
    private String Type = "", status = "";
    private ViewPager mViewPager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
  //  private Fragment_SectionPagerAdapter mFragment_SectionPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar (toolbar);
        getSupportActionBar().setTitle("covid testing");
        navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // startActivity(new Intent(MainActivity.this,HomeTeasting.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

                break;
            case R.id.home_Testing:
                startActivity(new Intent(MainActivity.this,HomeTeasting.class));

                break;
            case R.id.Lab_Testing:
                startActivity(new Intent(MainActivity.this,LabTesting.class));
                break;
            case R.id.map:
                  startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                onStart();
                //nav_logIn.setVisible(true);
                break;
        }
        return false;
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Menu menuNav = navigationView.getMenu();
        final MenuItem nav_profile = menuNav.findItem(R.id.nav_profile);
        final MenuItem home_Testing= menuNav.findItem(R.id.home_Testing);
        final MenuItem Labtesting = menuNav.findItem(R.id.Lab_Testing);

        MenuItem nav_logOut = menuNav.findItem(R.id.nav_logout);
        MenuItem nav_logIn = menuNav.findItem(R.id.nav_login);
        nav_profile.setVisible(false);
        home_Testing.setVisible(false);
       Labtesting.setVisible(false);
        nav_logIn.setVisible(true);
        nav_logOut.setVisible(false);


        if(currentUser==null){
            nav_logIn.setVisible(true);
           Labtesting.setVisible(false);
           home_Testing.setVisible(false);
        }else {
            nav_logOut.setVisible(true);
           // checkType();
            Labtesting.setVisible(true);
            home_Testing.setVisible(true);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showmap(View view) {
        startActivity(new Intent(MainActivity.this,MapsActivity.class));

    }

    public void doctorcall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:8707881873"));
        startActivity(callIntent);

    }

    public void ambulance(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:8707881873"));
        startActivity(callIntent);
    }
}