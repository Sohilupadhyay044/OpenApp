package com.example.openapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GpsActivity extends AppCompatActivity{

    private static final int REQUEST_LOCATION = 1;
//    Button button,button5;
//    TextView textView;
    ImageView imageView3;
//    LocationManager locationManager;
//    String lattitude,longitude;



    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
  //      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
   //  textView = (TextView)findViewById(R.id.text_location);
    //  button = (Button)findViewById(R.id.button_location);
     // button5 = (Button) findViewById(R.id.button5);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
//        button.setOnClickListener(this);
//
//
//       reference = FirebaseDatabase.getInstance().getReference("Current location");

    }

//    public void nextt (View view) {
//
//       Intent intent = new Intent(GpsActivity.this,complaint_activity.class);
//       startActivity(intent);
//       finish();
//   }



//    @Override
//    public void onClick( View view) {
//        button.setVisibility(View.VISIBLE);
//        button5.setVisibility(View.VISIBLE);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//
//        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            getLocation();
//        }
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (GpsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(GpsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//
//        } else {
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);
//
//            if (location != null) {
//                double latti = location.getLatitude();
//                double longi = location.getLongitude();
//                lattitude = String.valueOf(latti);
//                longitude = String.valueOf(longi);
//
//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);
//
//            } else  if (location1 != null) {
//                double latti = location1.getLatitude();
//                double longi = location1.getLongitude();
//                lattitude = String.valueOf(latti);
//                longitude = String.valueOf(longi);
//
//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);
//
//
//            } else  if (location2 != null) {
//                double latti = location2.getLatitude();
//                double longi = location2.getLongitude();
//                lattitude = String.valueOf(latti);
//                longitude = String.valueOf(longi);
//
//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);
//
//            }else{
//
//                Toast.makeText(this,"Unble to Trace your location", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }

//    protected void buildAlertMessageNoGps() {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Please Turn ON your GPS Connection")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick( final DialogInterface dialog, final int id) {
//                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick( final DialogInterface dialog, final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//
    public void mapsIcon( View view ) {
        startActivity(new Intent(this,MapsActivity.class));
    }

    public void CameraActivity(View view) {
        Intent intent = new Intent(GpsActivity.this,camactivity.class);
        startActivity(intent);
    }

    public void CommunityActivity(View view) {
        Intent intent = new Intent(GpsActivity.this,CommunityActivity.class);
        startActivity(intent);
    }

    public void SettingsIcon(View view) {
        Intent intent = new Intent(GpsActivity.this,SettingActivity.class);
        startActivity(intent);
    }
}
