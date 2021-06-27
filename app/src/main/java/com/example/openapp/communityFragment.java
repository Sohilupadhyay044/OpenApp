package com.example.openapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class communityFragment extends Fragment {

    private static final String TAG = communityFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    private StorageReference mStorage;


    AutoLinkTextView content;
    AlertDialog alertDialog;
    CircularImageView profilePic;
    TextView name, username;
    TextView textViewTime, textViewDate, textViewDevice;
    TextView textViewReTweet, textViewLikes;
    ImageView verifiedIcon;
    RoundedImageView imageTweet;
    boolean isProfilePic;

    Calendar calendar;
    int hour, minute;
    int mYear, mMonth, mDay;

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July",
            "Aug", "Sept", "Oct", "Nov", "Dec"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mStorage = FirebaseStorage.getInstance().getReference().child("images/");



//        Toolbar myToolbar =view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull(getSupportActionBar()).setTitle("Tweet with Image");
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        }
//        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


        profilePic = view.findViewById(R.id.profilePic);
        name = view.findViewById(R.id.name);
        verifiedIcon = view.findViewById(R.id.verified_icon);
        username = view.findViewById(R.id.username);
        content = view.findViewById(R.id.body);

        textViewTime = view.findViewById(R.id.textViewTime);
        imageTweet = view.findViewById(R.id.image_tweet);

        textViewDate = view.findViewById(R.id.textViewDate);
        textViewDevice = view.findViewById(R.id.textViewDevice);
        textViewReTweet = view.findViewById(R.id.textViewReTweet);
        textViewLikes = view.findViewById(R.id.textViewLikes);


        textViewReTweet.setText(R.string.number);
        textViewLikes.setText(R.string.number);
//
//        Typeface font = Typeface.createFromAsset(getAssets(), "font_helvetica_neue.ttf");
//        name.setTypeface(font);
//        username.setTypeface(font);
//        content.setTypeface(font);
//        textViewTime.setTypeface(font);
//        textViewDate.setTypeface(font);
//        textViewDevice.setTypeface(font);
//        textViewReTweet.setTypeface(font);
//        textViewLikes.setTypeface(font);


        //Setting Defaults Value
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        String format = getTimeFormat(hour);
        if (hour > 12) {
            hour = hour - 12;
        } else if (hour == 0) {
            hour = 12;
        }
        String strValueOfTime = hour + ":" + minute + " " + format;
        textViewTime.setText(strValueOfTime);

        mYear = calendar.get(Calendar.YEAR); // current year
        mMonth = calendar.get(Calendar.MONTH); // current month
        mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
        final String newDate = mDay + " " + months[mMonth - 1] + " " + mYear;
        textViewDate.setText(newDate);


        content.addAutoLinkMode(
                AutoLinkMode.MODE_HASHTAG,
                AutoLinkMode.MODE_PHONE,
                AutoLinkMode.MODE_URL,
                AutoLinkMode.MODE_EMAIL,
                AutoLinkMode.MODE_MENTION);
        content.setHashtagModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
        content.setEmailModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
        content.setMentionModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
        content.setUrlModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Name");

                final View customLayout = getLayoutInflater().inflate(R.layout.single_edit_dialog, null);
                builder.setView(customLayout);

                final EditText editTextName = customLayout.findViewById(R.id.editTextValue);
                editTextName.setInputType(InputType.TYPE_CLASS_TEXT);

                final Switch verified = customLayout.findViewById(R.id.verified);

                verified.setVisibility(View.VISIBLE);
                if (verifiedIcon.isShown()) {
                    verified.setChecked(true);
                } else {
                    verified.setChecked(false);
                }
                verified.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            verifiedIcon.setVisibility(View.VISIBLE);
                        } else {
                            verifiedIcon.setVisibility(View.GONE);
                        }
                    }
                });

                Log.i("Name Value", "onClick: name = " + name.getText().toString());
                if (name.getText().toString().contains("Tap to add name")) {
                    editTextName.setText("");
                } else {
                    String oldValue = name.getText().toString();
                    editTextName.setText(oldValue);
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editTextName.getText().length() > 0) {
                            name.setText(editTextName.getText());
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Username");

                final View customLayout = getLayoutInflater().inflate(R.layout.single_edit_dialog, null);
                builder.setView(customLayout);

                final EditText editTextUserName = customLayout.findViewById(R.id.editTextValue);
                editTextUserName.setInputType(InputType.TYPE_CLASS_TEXT);

                if (username.getText().length() > 0) {
                    String oldValue = username.getText().toString().split("@")[1];
                    editTextUserName.setText(oldValue);
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editTextUserName.getText().length() > 0) {
                            username.setText("@".concat(editTextUserName.getText().toString()));
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Tweet Body");

                final View customLayout = getLayoutInflater().inflate(R.layout.edit_text_for_tweet, null);
                builder.setView(customLayout);

                final EditText editTextBody = customLayout.findViewById(R.id.tweetContent);
                editTextBody.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextBody.setSingleLine(false);
                editTextBody.setLines(8);
                editTextBody.setMaxLines(30);
                editTextBody.setGravity(Gravity.START | Gravity.TOP);
                editTextBody.setHorizontalScrollBarEnabled(false);
                editTextBody.setBackgroundColor(Color.TRANSPARENT);

                final TextView textViewCounter = customLayout.findViewById(R.id.counter);

                if (content.getText().length() > 0) {
                    String oldValue = content.getText().toString();
                    editTextBody.setText(oldValue);
                }

                editTextBody.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String length = s.length() + "/280";
                        textViewCounter.setText(length);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editTextBody.getText().length() > 0) {
                            content.setAutoLinkText(editTextBody.getText().toString());
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_EMAIL,
                                AutoLinkMode.MODE_MENTION, AutoLinkMode.MODE_URL);
                        content.setHashtagModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
                        content.setEmailModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
                        content.setMentionModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));
                        content.setUrlModeColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.colorBlueType1));


                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String format;
                        format = getTimeFormat(selectedHour);

                        if (selectedHour > 12) {
                            selectedHour = selectedHour - 12;
                        } else if (selectedHour == 0) {
                            selectedHour = 12;
                        } else {
                            Log.i("SelectedHour", "onTimeSet: selected Hour" + selectedHour);
                        }

                        String strValueOfTime = selectedHour + ":" + selectedMinute + " " + format;
                        textViewTime.setText(strValueOfTime);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String newDate = dayOfMonth + " " + months[monthOfYear] + " " + year;
                                textViewDate.setText(newDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        textViewDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str = "Twitter for ";
                final String[] deviceArray = new String[]{"Android", "iPhone"};

                int checkedItem;
                String oldValue = textViewDevice.getText().toString();
                if (oldValue.contains("Android")) {
                    checkedItem = 0;
                } else {
                    checkedItem = 1;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Your Device");
                builder.setSingleChoiceItems(deviceArray, checkedItem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        Log.i("Device Selection", "onClick: Before Selection " + str);
                        String newValue = str + deviceArray[item];
                        textViewDevice.setText(newValue);
                        Log.i("Device Selection", "onClick: After Selection " + str);
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        textViewReTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Retweets");

                final View customLayout = getLayoutInflater().inflate(R.layout.single_edit_dialog, null);
                builder.setView(customLayout);

                final long[] value = new long[1];
                final EditText editTextNumValue = customLayout.findViewById(R.id.editTextValue);


                editTextNumValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextNumValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newValue;
                        value[0] = Long.parseLong(String.valueOf(editTextNumValue.getText()));

                        if (value[0] <= 999) {
                            newValue = editTextNumValue.getText().toString();
                        } else if (value[0] < 9999) {
                            String str = editTextNumValue.getText().toString();
                            newValue = str.charAt(0) + "," + str.charAt(1) + str.charAt(2) + str.charAt(3);
                        } else {

                            final String[] units = new String[]{"", "K", "M", "B"};
                            int digitGroups = (int) (Math.log10(value[0]) / Math.log10(1000));
                            newValue = new DecimalFormat("#,##0.#").format(value[0] / Math.pow(1000, digitGroups)) + "" + units[digitGroups];
                        }
                        textViewReTweet.setText(newValue);
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        textViewLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Likes");

                final View customLayout = getLayoutInflater().inflate(R.layout.single_edit_dialog, null);
                builder.setView(customLayout);

                final long[] value = new long[1];
                final EditText editTextNumValue = customLayout.findViewById(R.id.editTextValue);

                editTextNumValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextNumValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newValue;
                        value[0] = Long.parseLong(String.valueOf(editTextNumValue.getText()));
                        if (value[0] <= 999) {
                            newValue = editTextNumValue.getText().toString();
                        } else if (value[0] < 9999) {
                            String str = editTextNumValue.getText().toString();
                            newValue = str.charAt(0) + "," + str.charAt(1) + str.charAt(2) + str.charAt(3);
                        } else {

                            final String[] units = new String[]{"", "K", "M", "B"};
                            int digitGroups = (int) (Math.log10(value[0]) / Math.log10(1000));
                            newValue = new DecimalFormat("#,##0.#").format(value[0] / Math.pow(1000, digitGroups)) + "" + units[digitGroups];
                        }
                        textViewLikes.setText(newValue);
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public String getTimeFormat(int selectedHour) {
        String format;
        if (selectedHour == 0) {
            format = "AM";
        } else if (selectedHour == 12) {
            format = "PM";
        } else if (selectedHour > 12) {
            format = "PM";
        } else {
            format = "AM";
        }
        return format;
}

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setCancelable(false);
//        builder.setMessage("Do you really want to go back?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user pressed "yes", then he is allowed to exit from application
//                getActivity().finish();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }



    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    // navigating user to app settings
//    private void openSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);
//    }
}


