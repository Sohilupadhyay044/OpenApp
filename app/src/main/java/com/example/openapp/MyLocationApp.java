package com.example.openapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class MyLocationApp extends Service {
    LocationManager locMan;
    Location curLocation;
    Boolean locationChanged;
   private MediaPlayer mediaPlayer;


    Handler handler = new Handler();
    LocationListener gpsListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Log.w("GPS", "Started");
            if (curLocation == null) {
                curLocation = location;
                locationChanged = true;
            }
            if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude())
                locationChanged = false;
            else
                locationChanged = true;

            curLocation = location;

            if (locationChanged)
                locMan.removeUpdates(gpsListener);
        }
        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
            // Log.w("GPS", "Location changed", null);
        }
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            if (status == 0)// UnAvailable
            {
            } else if (status == 1)// Trying to Connect
            {
            } else if (status == 2) {// Available
            }
        }
    };
    @Override
    public void onCreate() {
        Toast.makeText(getBaseContext(), "Inside onCreate of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Pothole Detected");
      mediaPlayer=MediaPlayer.create(this,R.raw.alarm);
        super.onCreate();
        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
           /*if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                   locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000, 1, gpsListener);
           } else {
                   this.startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
           }
 */
        if (curLocation != null) {
            double lat = curLocation.getLatitude();
            double lng = curLocation.getLongitude();
            Toast.makeText(getBaseContext(), "Lat : " + String.valueOf(lat) + "\n Long : " + String.valueOf(lng), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getBaseContext(), "Didn Get any location", Toast.LENGTH_LONG).show();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void showNotificationAndStartForeGround() {
//        String NOTIFICATION_CHANNEL_ID = "lloyd";
//        String channelName = "Background Service";
//        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
//        chan.setLightColor(Color.BLUE);
//
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        assert manager != null;
//        manager.createNotificationChannel(chan);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//        Notification notification = notificationBuilder.setOngoing(true)
//                .setContentTitle("App is running in background")
//                .setContentText("Pothole Detected")
//                .setPriority(NotificationManager.IMPORTANCE_MIN)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .build();
//        startForeground(2, notification);
//    }



    final String TAG = "LocationService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(), "Inside onStartCommand of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStartCommand of Service");
       mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       mediaPlayer.stop();
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }


    @Override
    public void onStart(Intent i, int startId) {
        Toast.makeText(getBaseContext(), "Inside onStart of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStart of Service");

        handler.postDelayed(GpsFinder, 5000);// will start after 5 seconds
    }

    public IBinder onBind(Intent arg0) {
        Log.e(TAG, "Inside onBind of Service");
        return null;
    }

    public Runnable GpsFinder = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub

            if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            }
            else
            {
                getApplicationContext().startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
            }
            if (curLocation != null) {
                double lat = curLocation.getLatitude();
                double lng = curLocation.getLongitude();
                Toast.makeText(getBaseContext(),"Lat : " + String.valueOf(lat) + "\n Long : "+ String.valueOf(lng), Toast.LENGTH_LONG).show();
            }
            handler.postDelayed(GpsFinder,5000);// register again to start after 5 seconds...
        }
    };
}
