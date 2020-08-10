package com.example.openapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class InstallActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
    }

    public void GoAhead( View view ) {
        Intent intent = new Intent(getApplicationContext(), camactivity.class);
        startActivity(intent);
        finish();
    }

    public void link( View view ) {

       Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

        //Copy App URL from Google Play Store.
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.peace.ArMeasure"));

        startActivity(intent);
    }
}