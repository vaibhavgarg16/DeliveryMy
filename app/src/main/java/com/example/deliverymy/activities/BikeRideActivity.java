package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deliverymy.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class BikeRideActivity extends AppCompatActivity implements OnMapReadyCallback {

    CardView card, cardExpand, cardCars;
    TextView textView;
    LinearLayout trip;
    TextView submit;
    LinearLayout l1, l2, l3;
    ImageView addStopage, lessStopage, backpress;
    FrameLayout addStop;
    View newview;
    float slideoffset = 1;
    LinearLayout dragLayout;

    //google map object
    GoogleMap mMap;
    boolean locationPermission = false;
    Location myLocation = null;
    Location myUpdatedLocation = null;
    float Bearing = 0;
    boolean AnimationStatus = false;
    static Marker carMarker;
    Bitmap BitMapMarker;
    private final static int LOCATION_REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_ride);

        SlidingUpPanelLayout layout = findViewById(R.id.slidingUp);
        dragLayout = findViewById(R.id.dragLayout);
        textView = findViewById(R.id.textView);
        card = findViewById(R.id.card);
        trip = findViewById(R.id.trip);
        cardExpand = findViewById(R.id.cardExpand);
        submit = findViewById(R.id.submit);
        cardCars = findViewById(R.id.cardCars);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        addStopage = findViewById(R.id.addStopage);
        lessStopage = findViewById(R.id.lessStopage);
        backpress = findViewById(R.id.backpress);
        addStop = findViewById(R.id.addStop);
        newview = findViewById(R.id.newview);

        requestPermision();
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
        Bitmap b = bitmapdraw.getBitmap();
        BitMapMarker = Bitmap.createScaledBitmap(b, 110, 60, false);

        layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                findViewById(R.id.cardExpand).setVisibility(View.GONE);
                slideoffset = slideOffset;
                textView.setAlpha(1 - slideOffset);
                card.setAlpha(1 - slideOffset);

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    cardExpand.setVisibility(View.VISIBLE);
                    trip.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                } else if (newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    cardExpand.setVisibility(View.GONE);
                    trip.setVisibility(View.GONE);
                    card.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

        addStopage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStop.setVisibility(View.VISIBLE);
                newview.setVisibility(View.VISIBLE);
                addStopage.setVisibility(View.GONE);
            }
        });

        lessStopage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStop.setVisibility(View.GONE);
                newview.setVisibility(View.GONE);
                addStopage.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BikeRideActivity.this, BikeRideActivity2.class));

            }
        });
    }

    /*@Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(3.1390, 101.6869);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Kuala Lumpur, Malaysia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

    //to get user location
    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (AnimationStatus) {
                    myUpdatedLocation = location;
                } else {
                    myLocation = location;
                    myUpdatedLocation = location;
                    LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                    carMarker = mMap.addMarker(new MarkerOptions().position(latlng).
                            flat(true).icon(BitmapDescriptorFactory.fromBitmap(BitMapMarker)));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                            latlng, 17f);
                    mMap.animateCamera(cameraUpdate);
                }
                Bearing = location.getBearing();
                LatLng updatedLatLng = new LatLng(myUpdatedLocation.getLatitude(), myUpdatedLocation.getLongitude());
                changePositionSmoothly(carMarker, updatedLatLng, Bearing);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        getMyLocation();
    }

    void changePositionSmoothly(final Marker myMarker, final LatLng newLatLng, final Float bearing) {

        final LatLng startPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        final LatLng finalPosition = newLatLng;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 3000;
        final boolean hideMarker = false;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                myMarker.setRotation(bearing);
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                        startPosition.longitude * (1 - t) + finalPosition.longitude * t);

                myMarker.setPosition(currentPosition);

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        myMarker.setVisible(false);
                    } else {
                        myMarker.setVisible(true);
                    }
                }
                myLocation.setLatitude(newLatLng.latitude);
                myLocation.setLongitude(newLatLng.longitude);
            }
        });
    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            LocationstatusCheck();
            locationPermission = true;
            //init google map fragment to show map.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationstatusCheck();
                    //if permission granted.
                    locationPermission = true;
                    //init google map fragment to show map.
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                    // getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public void LocationstatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}