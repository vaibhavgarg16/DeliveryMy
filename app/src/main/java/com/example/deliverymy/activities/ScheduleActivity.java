package com.example.deliverymy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.deliverymy.R;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button submit = findViewById(R.id.submit);
        TextInputEditText pickuptime = findViewById(R.id.pickuptime);
        TextInputEditText pickupdate = findViewById(R.id.pickupdate);

        pickuptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SingleDateAndTimePickerDialog.Builder(ScheduleActivity.this)
                        .bottomSheet()
                        .curved()
                        .displayAmPm(true)
                        .displayMinutes(true)
                        .displayHours(true)
                        .displayDays(false)
                        .displayMonth(false)
                        .displayYears(false)
                        .displayDaysOfMonth(false)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                String currentdate = date.getHours()+":"+date.getMinutes();
                                pickuptime.setText(currentdate);
                            }
                        }).display();
            }
        });

        pickupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SingleDateAndTimePickerDialog.Builder(ScheduleActivity.this)
                        .bottomSheet()
                        .curved()
                        .displayMinutes(false)
                        .displayHours(false)
                        .displayDays(false)
                        .displayMonth(true)
                        .displayYears(true)
                        .displayDaysOfMonth(true)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                String currentdate = date.toString();
                                String[] separated = currentdate.split(" ");
                                String currentdate1 = date.getMonth()+"/"+date.getDate()+"/"+separated[5];
                                pickupdate.setText(currentdate1);
                            }
                        }).display();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}