package com.example.deliverymy.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverymy.R;

import java.util.Calendar;
import java.util.Date;

public class TrackOrderActivity extends AppCompatActivity {

    TextView searching_datetime, addNote;
    ImageView back_icon;
    RelativeLayout track_order_div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        back_icon = findViewById(R.id.back_icon);
        searching_datetime = findViewById(R.id.searching_datetime);
        addNote = findViewById(R.id.addNote);
        track_order_div = findViewById(R.id.track_order_div);
        Date currentTime = Calendar.getInstance().getTime();
        searching_datetime.setText(String.valueOf(currentTime));


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        track_order_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackOrderActivity.this, MapTestActivity.class));
            }
        });
    }
}