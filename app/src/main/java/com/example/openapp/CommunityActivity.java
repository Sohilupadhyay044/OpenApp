package com.example.openapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
    }
    public void CameraIcon(View view) {
        Intent intent = new Intent(CommunityActivity.this,camactivity.class);
        startActivity(intent);
    }
    public void SettingIcon(View view) {

        Intent intent = new Intent(CommunityActivity.this,SettingActivity.class);
        startActivity(intent);
    }
    public void LocationIcon(View view) {
        Intent intent = new Intent(CommunityActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}