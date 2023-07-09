package com.example.deliverymy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverymy.R;
import com.example.deliverymy.utils.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class RideActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleApiClient.OnConnectionFailedListener {

    CardView card, cardExpand, cardCars;
    TextView textView,textView2;
    LinearLayout trip;
    TextView submit;
    LinearLayout l1, l2, l3;
    ImageView addStopage, lessStopage, backpress;
    FrameLayout addStop;
    View newview;
    float slideoffset = 1;
    LinearLayout dragLayout;
    TextInputEditText Startlocation, Destination;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 123;
    private static final int AUTOCOMPLETE_REQUEST_CODE2 = 1234;
    private static final String GOOGLE_PLACES_API_KEY = "AIzaSyDT2Mzp7PGGi3n0x5G9oAIOU6ti9UKL7to";
    private static LatLngBounds LAT_LNG_BOUNDS = null;
    private boolean permissionDenied = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap map;
    String startStr, destinationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        Places.initialize(getApplicationContext(), GOOGLE_PLACES_API_KEY);

        SlidingUpPanelLayout layout = findViewById(R.id.slidingUp);
        dragLayout = findViewById(R.id.dragLayout);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        Startlocation = findViewById(R.id.Startlocation);
        Destination = findViewById(R.id.Destination);
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
        addStop = findViewById(R.id.addStop);
        newview = findViewById(R.id.newview);
        backpress = findViewById(R.id.backpress);

        //Below code and condition for check GPS is on or off in the device
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        LAT_LNG_BOUNDS = new LatLngBounds(
                new LatLng(-40, -168), new LatLng(71, 136));

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Startlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAutoCompleteIntent(AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAutoCompleteIntent(AUTOCOMPLETE_REQUEST_CODE2);
            }
        });


        layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                findViewById(R.id.cardExpand).setVisibility(View.GONE);
                slideoffset = slideOffset;
                textView.setAlpha(1 - slideOffset);
                textView2.setAlpha(1 - slideOffset);
                card.setAlpha(1 - slideOffset);

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    cardExpand.setVisibility(View.VISIBLE);
                    trip.setVisibility(View.VISIBLE);
//                    submit.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                } else if (newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    cardExpand.setVisibility(View.GONE);
                    trip.setVisibility(View.GONE);
//                    submit.setVisibility(View.GONE);
                    card.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
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
                if(validate()) {
                    Intent intent = new Intent(RideActivity.this, RideActivity2.class);
                    intent.putExtra("start", startStr);
                    intent.putExtra("destination", destinationStr);
                    startActivity(intent);
//                    startActivity(new Intent(RideActivity.this, RideActivity2.class));
                }
            }
        });
    }


    boolean validate() {
        if (TextUtils.isEmpty(Startlocation.getText())) {
            Startlocation.setError("Please enter Start location");
            Startlocation.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Destination.getText())) {
            Destination.setError("Please enter Destination");
            Destination.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void buildAlertMessageNoGps() {    //GPS check method
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(@NonNull Location location) {
                        float zoomLevel = 16.0f;
                        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
                        getAddress(location.getLatitude(), location.getLongitude());
                    }
                });
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(RideActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getAdminArea();

            Startlocation.setText(add);
            startStr= lat+","+lng;
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            permissionDenied = true;
        }
    }

    private void createAutoCompleteIntent(int AUTOCOMPLETE_REQUEST_CODE) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(RideActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("tag", "Place: " + place.getName() + ", " + place.getLatLng());
                Startlocation.setText(place.getName());
                startStr = place.getLatLng().latitude+","+place.getLatLng().longitude;

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }else if(requestCode == AUTOCOMPLETE_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("tag", "Place: " + place.getName() + ", " + place.getLatLng());
                Destination.setText(place.getName());
//                tvPlaceId.setText(place.getId());
                destinationStr = place.getLatLng().latitude+","+place.getLatLng().longitude;
//                tvLatLon.setText(String.valueOf(place.getLatLng()));

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}