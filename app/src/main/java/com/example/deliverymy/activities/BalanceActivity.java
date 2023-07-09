package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliverymy.R;

public class BalanceActivity extends AppCompatActivity {

    ImageView image;
    TextView text;
    Button btnAddBalanceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);


        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        btnAddBalanceId = findViewById(R.id.btnAddBalanceId);

        text.setText("Account Balance");
        image.setImageResource(R.drawable.back_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddBalanceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Add card screen
                startActivity(new Intent(BalanceActivity.this, AddCardScreen.class));
            }
        });
    }
}