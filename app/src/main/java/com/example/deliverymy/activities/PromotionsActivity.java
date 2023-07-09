package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliverymy.R;

public class PromotionsActivity extends AppCompatActivity {

    ImageView image;
    TextView text;
    Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        btnApply = findViewById(R.id.btnApply);

        text.setText("Promotions");
        image.setImageResource(R.drawable.back_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromotionsActivity.this, PromoCodeListActivity.class));
            }
        });
    }
}