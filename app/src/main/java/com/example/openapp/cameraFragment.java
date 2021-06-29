package com.example.openapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class cameraFragment extends Fragment {

    private static final int REQUEST_CODE = 1001;
    public static final int CAMERA_LAUNCHER = 9999;
    ImageView imageView;
    Button button2,button,cnext;
    LottieAnimationView imgClick;



    private StorageReference mStorageRef;
    String url ="https://firebasestorage.googleapis.com/v0/b/firestoreconn.appspot.com/o/american.jpg?alt=media&token=bd742293-3267-4584-a89e-a9dc74d1868b";
    Uri uri;
    private Object view;


    public cameraFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_camera, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgClick =(LottieAnimationView) view.findViewById(R.id.lottie);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        button2 = (Button) view.findViewById(R.id.button3);
        button = (Button) view.findViewById(R.id.imageView9);
        cnext = (Button) view.findViewById(R.id.cnext);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Picasso.get().load(url).into(imageView);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setVisibility(View.VISIBLE);
                StorageReference riversRef = mStorageRef.child("images/"+uri.getLastPathSegment());
                riversRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                Log.d("abhi",""+downloadUrl);
                                Toast.makeText(getContext(), "Data Save SuccessFully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(getContext(), "Koi Problem Hai", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button2.setEnabled(true);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent, 101);
            }
        });

imgClick.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        imgClick.setVisibility(View.INVISIBLE);
        button.setEnabled(true);
        Intent OpenApp = getActivity().getPackageManager().getLaunchIntentForPackage("com.peace.ArMeasure");
        startActivity(OpenApp);


    }
});


    }



    public void NextActivity( View view ) {
        locationFragment locationFgmt = new locationFragment();
        getChildFragmentManager().beginTransaction().add(R.id.flFragment,locationFgmt, "home").addToBackStack("hello").commit();
    }




//    public void OpenApp( View view ) {
//
//    }

    public void OpenCamera( View view ) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_LAUNCHER);

        Toast.makeText(getContext(), "Camera open sucessfully", Toast.LENGTH_SHORT).show();

    }

    public void Gallery( View view ) {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_LAUNCHER) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadImage(View view) {

    }

    public void cnext(View view) {}



}