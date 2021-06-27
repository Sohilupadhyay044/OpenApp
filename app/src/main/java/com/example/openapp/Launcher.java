package com.example.openapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Launcher extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        defView();
    }

    private void defView() {

        locationFragment locationFgmt = new locationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.flFragment,locationFgmt, "home").addToBackStack("hello").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.locationFrag:

                    selectedFragment = new locationFragment();
                    break;
                case R.id.cameraFrag:
                    selectedFragment = new cameraFragment();
                    break;
                case R.id.communityFrag:
                    selectedFragment = new communityFragment();
                    break;
                case R.id.accountFrag:
                    selectedFragment = new accountFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).addToBackStack("hello").commit();
            return true;
        }


    };


}