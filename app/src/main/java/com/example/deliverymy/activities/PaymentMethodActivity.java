package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.deliverymy.R;

public class PaymentMethodActivity extends AppCompatActivity {

    ImageView image;
    TextView text;
    RelativeLayout balanceLayoutId, creditLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        balanceLayoutId = findViewById(R.id.balanceLayoutId);
        creditLayoutId = findViewById(R.id.creditLayoutId);

        text.setText("My Wallet");
        image.setImageResource(R.drawable.back_icon);

        balanceLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Add Card activity
                startActivity(new Intent(PaymentMethodActivity.this, AddCardScreen.class));
            }
        });

        creditLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Add Card activity
                startActivity(new Intent(PaymentMethodActivity.this, AddCardScreen.class));
            }
        });
    }
}