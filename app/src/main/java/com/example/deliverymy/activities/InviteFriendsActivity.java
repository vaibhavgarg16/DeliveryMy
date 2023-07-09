package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliverymy.R;

public class InviteFriendsActivity extends AppCompatActivity {

    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);

        text.setText("Invite a friend");
        image.setImageResource(R.drawable.back_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");

        String app_url = "DeliveryMY app is in working process.";
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}