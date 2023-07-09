package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deliverymy.MainActivity;
import com.example.deliverymy.R;

public class OtpActivity extends AppCompatActivity {

    Button btnSubmitId;
    TextView counter,resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        btnSubmitId = findViewById(R.id.btnSubmitId);
        counter = findViewById(R.id.counter);
        resend = findViewById(R.id.resend);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 10000)
                    counter.setText("00:"+(millisUntilFinished / 1000));
                else
                    counter.setText("00:"+ 0 +(millisUntilFinished / 1000));
            }

            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                counter.setVisibility(View.GONE);
            }
        }.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend.setVisibility(View.GONE);
                counter.setVisibility(View.VISIBLE);

                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (millisUntilFinished > 10000)
                            counter.setText("00:"+(millisUntilFinished / 1000));
                        else
                            counter.setText("00:"+ 0 +(millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        resend.setVisibility(View.VISIBLE);
                        counter.setVisibility(View.GONE);
                    }
                }.start();
            }
        });

        btnSubmitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to home screen
                startActivity(new Intent(OtpActivity.this, MainActivity.class));
            }
        });
    }
}