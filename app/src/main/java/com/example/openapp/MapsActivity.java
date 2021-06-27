package com.example.openapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
      com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;

    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;


    ArrayList<LatLng> arrayList = new ArrayList<>();

    LatLng location = new LatLng(27.1570777,81.9775159);
    LatLng location1 = new LatLng(27.1572083,81.9764481);
    LatLng location2 = new LatLng(27.1587912,81.97914);
    LatLng location3 = new LatLng(27.1593377,81.9780627);
    LatLng location4 = new LatLng(27.158827,81.9795675);
    LatLng location5 = new LatLng(27.1593377,81.9780627);
    LatLng location6 = new LatLng(27.158827,81.9795675);
    LatLng location7 = new LatLng(27.158827,81.9795675);

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       firebaseDatabase = FirebaseDatabase.getInstance();
   reference = firebaseDatabase.getReference("Current Location");


   arrayList.add(location);
   arrayList.add(location1);
   arrayList.add(location2);
   arrayList.add(location3);
   arrayList.add(location4);
   arrayList.add(location5);
   arrayList.add(location6);
   arrayList.add(location7);



        if (requestSinglePermission()) {


            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

          mGoogleApiClient = new GoogleApiClient.Builder(this)
                  .addConnectionCallbacks(this)
                  .addOnConnectionFailedListener(this)
                  .addApi(LocationServices.API)
                  .build();

          mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

          checkLocation();




        }
    }

    private boolean checkLocation() {

        if(!isLocationEnabled()){
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Location Settings is set to 'Off' .\nPlease Enable Location to "+
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialogInterface, int i ) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialogInterface, int i ) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {

        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted( PermissionGrantedResponse response ) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied( PermissionDeniedResponse response ) {
                      if(response.isPermanentlyDenied()){
                          isPermission = false;
                    }
                }

                    @Override
                    public void onPermissionRationaleShouldBeShown( PermissionRequest permission, PermissionToken token ) {

                    }
                }).check();
        return isPermission;

    }




        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady (GoogleMap googleMap ){
            mMap = googleMap;

            for(int i=0; i<arrayList.size(); i++){
                mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Potholes Detected Be CareFul")
                        .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic__39226)));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(16.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
            }



          if(latLng!=null){
              mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"));
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15F));
          }
        }

        private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
            Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
            vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight());

            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }

        @Override
        public void onConnected (@Nullable Bundle bundle ){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED){
                return;
            }
            startLocationUpdates();
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(mLocation == null){
                startLocationUpdates();
            }
            else{
                Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            }

        }

    private void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest,this);
    }

    @Override
        public void onConnectionSuspended ( int i ){



        }

        @Override
        public void onConnectionFailed (@NonNull ConnectionResult connectionResult ){

        }

        @Override
        public void onLocationChanged (Location location ){

            String msg = "Updated Location: " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude());
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        LocationHelper helper = new LocationHelper(location.getLongitude(), location.getLatitude());

    FirebaseDatabase.getInstance().getReference("Current Location")
                    .setValue(helper).addOnCompleteListener(new OnCompleteListener <Void>() {
              @Override
              public void onComplete( @NonNull Task <Void> task ) {

                    if(task.isSuccessful()){
                       Toast.makeText(MapsActivity.this, "Location is Saved", Toast.LENGTH_SHORT).show();
                   }
                   else{
                        Toast.makeText(MapsActivity.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                   }
               }
            });



            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

    @Override
    protected void onStart() {
        super.onStart();

        if(mGoogleApiClient !=null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
}





