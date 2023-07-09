package com.example.deliverymy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class BikeRideActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    Button submit, submit2;
    LinearLayout linear2, linear1, linear3;
    GoogleMap mMap;
    TextView addNote, showMore, showLess;
    LinearLayout radiogroup;
    LottieAnimationView animationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_ride2);

        SlidingUpPanelLayout layout = findViewById(R.id.slidingUp);

        submit = findViewById(R.id.submit);
        submit2 = findViewById(R.id.submit2);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        addNote = findViewById(R.id.addNote);
        showMore = findViewById(R.id.showMore);
        showLess = findViewById(R.id.showLess);
        radiogroup = findViewById(R.id.radiogroup);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);

        submit.setOnClickListener(view -> {
            linear2.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.GONE);
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(BikeRideActivity2.this);
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
                                startActivity(new Intent(BikeRideActivity2.this, TrackOrderActivity.class));
                            }
                        }, 4500);
//                startActivity(new Intent(BikeRideActivity2.this, TrackOrderActivity.class));
                    }
                });
                dialog.show();
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BikeRideActivity2.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customLayout = inflater.inflate(R.layout.addnote_layout, null);
                builder.setView(customLayout);
                builder.setTitle("Add Note");
                EditText textArea_information = customLayout.findViewById(R.id.textArea_information);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(BikeRideActivity2.this, "Success", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(3.1390, 101.6869);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Kuala Lumpur, Malaysia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}