 package com.example.openapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class camactivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1001;
    public static final int CAMERA_LAUNCHER = 9999;
    ImageView imageView;
    Button button2,button,cnext;
    private StorageReference mStorageRef;
    String url ="https://firebasestorage.googleapis.com/v0/b/firestoreconn.appspot.com/o/american.jpg?alt=media&token=bd742293-3267-4584-a89e-a9dc74d1868b";
    Uri uri;
    private Object view;



    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camactivity);
       imageView = (ImageView) findViewById(R.id.imageView2);
        button2 = (Button) findViewById(R.id.button3);
        button = (Button) findViewById(R.id.imageView9);
      cnext = (Button) findViewById(R.id.cnext);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Picasso.get().load(url).into(imageView);

    }


    public void NextActivity( View view ) {
        Intent intent = new Intent(camactivity.this, GpsActivity.class);
   //     intent.putExtra("resid", R.drawable.ic_launcher_background);
        startActivity(intent);
    }


    public void OpenApp( View view ) {
        button.setEnabled(true);
        Intent OpenApp = getPackageManager().getLaunchIntentForPackage("com.peace.ArMeasure");
        startActivity(OpenApp);
    }

    public void OpenCamera( View view ) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_LAUNCHER);

            Toast.makeText(this, "Camera open sucessfully", Toast.LENGTH_SHORT).show();

    }


      public void Gallery( View view ) {

              button2.setEnabled(true);

              Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
              intent.setType("image/");
              startActivityForResult(intent, 101);
          }

        @Override
        protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_LAUNCHER) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    public void uploadImage(View view) {
        button2.setVisibility(View.VISIBLE);
        cnext.setVisibility(View.VISIBLE);
        StorageReference riversRef = mStorageRef.child("images/"+uri.getLastPathSegment());
        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        Log.d("abhi",""+downloadUrl);
                        Toast.makeText(camactivity.this, "Data Save SuccessFully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(camactivity.this, "Koi Problem Hai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void cnext(View view) {

        Intent intent = new Intent(camactivity.this,GpsActivity.class);
        startActivity(intent);
        finish();
    }

    public void MapIcon(View view) {
        Intent intent = new Intent(camactivity.this,GpsActivity.class);
        startActivity(intent);
    }


    public void CommunityActivity(View view) {
        Intent intent = new Intent(camactivity.this,CommunityActivity.class);
        startActivity(intent);

    }

    public void SettingIcon(View view) {
        Intent intent = new Intent(camactivity.this,SettingActivity.class);
        startActivity(intent);
    }
}





