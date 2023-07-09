package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.deliverymy.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class VerificationActivity extends AppCompatActivity {

    Button btnContinueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        btnContinueId = findViewById(R.id.btnContinueId);
        btnContinueId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to OTP activity
                startActivity(new Intent(VerificationActivity.this, OtpActivity.class));
            }
        });
    }
}