package com.example.openapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class complaint_activity extends AppCompatActivity {

    private EditText ename, eemail, ePhone, eadhar, eissue;
    private Button btfinish;

    Button button00;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_activity);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ComplaintData");

        ename = findViewById(R.id.etname);
        eemail = findViewById(R.id.etemail);
        ePhone = findViewById(R.id.etPhone);
        eadhar = findViewById(R.id.etadhar);
        eissue = findViewById(R.id.etissue);

        button00 = findViewById(R.id.Khatam);



        final Button btfinish = (Button) findViewById(R.id.btfinish);


        eemail.setEnabled(false);
        ePhone.setEnabled(false);
        eadhar.setEnabled(false);
        eissue.setEnabled(false);
        btfinish.setEnabled(false);

        ename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                if(charSequence.toString().equals("")) {
                    eemail.setEnabled(false);

                }
                else {
                    eemail.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

        eemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                if(charSequence.toString().equals("")) {
                    eadhar.setEnabled(false);

                }
                else {
                    eadhar.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

        eadhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                if(charSequence.toString().equals("")) {
                    ePhone.setEnabled(false);

                }
                else {
                    ePhone.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

        ePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                if(charSequence.toString().equals("")) {
                    eissue.setEnabled(false);

                }
                else {
                    eissue.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

        eissue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                if(charSequence.toString().equals("")) {
                    btfinish.setEnabled(false);

                }
                else {
                    btfinish.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged( Editable editable ) {

            }
        });

    }

    public void btfinish( View view ) {

        button00.setEnabled(true);

                        String name = ename.getText().toString();
                        String email = eemail.getText().toString();
                        String Phone = ePhone.getText().toString();
                        String adhar = eadhar.getText().toString();
                        String issue = eissue.getText().toString();


                        Complaint c = new Complaint(name, email, Phone, adhar, issue);
                        reference.push().setValue(c)
                                .addOnCompleteListener(new OnCompleteListener <Void>() {
                                    @Override
                                    public void onComplete( @NonNull Task <Void> task ) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(complaint_activity.this, "Complaint is Registered.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(complaint_activity.this, "Data has not been Saved", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                    }


    public void Khatam( View view ) {

        Toast.makeText(this, "Thank You.We Will Contact You Soon", Toast.LENGTH_LONG).show();

    }
}
