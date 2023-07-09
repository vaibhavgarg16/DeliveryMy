package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliverymy.R;

public class PromoCodeListActivity extends AppCompatActivity {

    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code_list);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        text.setText("Promotions List");
        image.setImageResource(R.drawable.back_icon);
    }
}