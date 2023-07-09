package com.example.deliverymy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.deliverymy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class InProgressFragment extends Fragment implements OnMapReadyCallback {

    CardView card,cardExpand;
    TextView textView;
    LinearLayout trip;
//    RelativeLayout map;
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);

        SlidingUpPanelLayout layout = view.findViewById(R.id.slidingUp);
        textView = view.findViewById(R.id.textView);
        card = view.findViewById(R.id.card);
        trip = view.findViewById(R.id.trip);
        cardExpand = view.findViewById(R.id.cardExpand);
//        map = view.findViewById(R.id.map);

        FragmentManager myFm = getActivity().getSupportFragmentManager();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) myFm
                .findFragmentById(R.id.map1);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                view.findViewById(R.id.cardExpand).setVisibility(View.GONE);

                textView.setAlpha(1 - slideOffset);
                card.setAlpha(1 - slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    cardExpand.setVisibility(View.VISIBLE);
                    trip.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    cardExpand.setVisibility(View.GONE);
                    trip.setVisibility(View.GONE);
                    card.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(26, 75);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Jaipur, India"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}