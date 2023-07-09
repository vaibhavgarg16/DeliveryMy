package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.deliverymy.R;

public class MyWallet extends AppCompatActivity {

    ImageView image;
    TextView text;
    RelativeLayout balanceLayoutId, paymentMethodLayoutId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        balanceLayoutId = findViewById(R.id.balanceLayoutId);
        paymentMethodLayoutId = findViewById(R.id.paymentMethodLayoutId);

        text.setText("My Wallet");
        image.setImageResource(R.drawable.back_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        balanceLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Balance Activity
                startActivity(new Intent(MyWallet.this, BalanceActivity.class));
            }
        });

        paymentMethodLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Payment activity
                startActivity(new Intent(MyWallet.this, PaymentMethodActivity.class));
            }
        });
    }
}