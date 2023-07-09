package com.example.deliverymy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliverymy.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MapTestActivity extends AppCompatActivity {

    private static final String GOOGLE_PLACES_API_KEY = "AIzaSyDT2Mzp7PGGi3n0x5G9oAIOU6ti9UKL7to";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 123;
    private TextView tvLocationName, tvPlaceId, tvLatLon;
    Button btnPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);
        Places.initialize(getApplicationContext(), GOOGLE_PLACES_API_KEY);

        tvLocationName = findViewById(R.id.textView6);
        tvPlaceId = findViewById(R.id.textView7);
        tvLatLon = findViewById(R.id.textView5);

        btnPlaces = (Button) findViewById(R.id.btnPlaces);

        btnPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAutoCompleteIntent();

            }
        });
    }

    private void createAutoCompleteIntent() {
        tvLocationName.setText("");
        tvPlaceId.setText("");
        tvLatLon.setText("");
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(MapTestActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("tag", "Place: " + place.getName() + ", " + place.getLatLng());
                tvLocationName.setText(place.getName());
                tvPlaceId.setText(place.getId());
                tvLatLon.setText(String.valueOf(place.getLatLng()));

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


}