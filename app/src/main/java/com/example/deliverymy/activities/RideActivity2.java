package com.example.deliverymy.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.deliverymy.R;
import com.example.deliverymy.utils.FetchURL;
import com.example.deliverymy.utils.TaskLoadedCallback;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Date;

public class RideActivity2 extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    Button submit, submit2;
    LinearLayout linear2, linear1, linear3;
    GoogleMap mMap;
    TextView addNote, showMore, showLess, addpayment, schedule;
    LinearLayout radiogroup;
    LottieAnimationView animationView;
    private String mOrigin;
    private String mDestination;
    LatLng location1, location2;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride2);
        SlidingUpPanelLayout layout = findViewById(R.id.slidingUp);

        submit = findViewById(R.id.submit);
        submit2 = findViewById(R.id.submit2);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        addNote = findViewById(R.id.addNote);
        showMore = findViewById(R.id.showMore);
        showLess = findViewById(R.id.showLess);
        addpayment = findViewById(R.id.addpayment);
        radiogroup = findViewById(R.id.radiogroup);
        schedule = findViewById(R.id.schedule);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        submit.setOnClickListener(view -> {
            linear2.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.GONE);
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(RideActivity2.this);
                dialog.setContentView(R.layout.dialog);
                TextView confirm = dialog.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        linear3.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.GONE);
                        linear1.setVisibility(View.GONE);

                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                animationView.pauseAnimation();
                                startActivity(new Intent(RideActivity2.this, TrackOrderActivity.class));
                            }
                        }, 4500);
                    }
                });
                dialog.show();
            }
        });

        addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RideActivity2.this, MyWallet.class));
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RideActivity2.this, ScheduleActivity.class));
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity2.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customLayout = inflater.inflate(R.layout.addnote_layout, null);
                builder.setView(customLayout);
                builder.setTitle("Add Note");
                EditText textArea_information = customLayout.findViewById(R.id.textArea_information);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(RideActivity2.this, "Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMore.setVisibility(View.GONE);
                showLess.setVisibility(View.VISIBLE);
                radiogroup.setVisibility(View.VISIBLE);
            }
        });
        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLess.setVisibility(View.GONE);
                showMore.setVisibility(View.VISIBLE);
                radiogroup.setVisibility(View.GONE);
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            mOrigin = null;
            mDestination = null;
        } else {
            mOrigin = extras.getString("start");
            mDestination = extras.getString("destination");

            String[] latlong = mOrigin.split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            String[] latlong2 = mDestination.split(",");
            double latitude2 = Double.parseDouble(latlong2[0]);
            double longitude2 = Double.parseDouble(latlong2[1]);

            location1 = new LatLng(latitude, longitude);
            location2 = new LatLng(latitude2, longitude2);

            place1 = new MarkerOptions().position(location1).title("Location 1");
            place2 = new MarkerOptions().position(location2).title("Location 2");
//            MapFragment mapFragment = (MapFragment) getFragmentManager()
//                    .findFragmentById(R.id.mapNearBy);
//            mapFragment.getMapAsync(this);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            new FetchURL(RideActivity2.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 16.0f; //This goes up to 21

        enableMyLocation();
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, zoomLevel));
    }

    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.googleApiKey);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}