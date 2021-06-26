package com.example.openapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void CommunityActivity(View view) {
        Intent intent = new Intent(SettingActivity.this,CommunityActivity.class);
        startActivity(intent);
    }

    public void CamaeraIcon(View view) {
        Intent intent = new Intent(SettingActivity.this,camactivity.class);
        startActivity(intent);
    }


    public void MapsIcon(View view) {
        Intent intent = new Intent(SettingActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}