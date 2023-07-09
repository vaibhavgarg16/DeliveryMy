package com.example.deliverymy.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliverymy.R;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.List;

public class AddCardScreen extends AppCompatActivity {

    ImageView image,imgCam;
    TextView text;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_screen);

        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        btnAdd = findViewById(R.id.btnAdd);
        imgCam = findViewById(R.id.imgCam);

        text.setText("Add Card");
        image.setImageResource(R.drawable.back_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddCardScreen.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                //Jump to MyWallet screen
                startActivity(new Intent(AddCardScreen.this, MyWallet.class));
            }
        });

        imgCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(AddCardScreen.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.JPG)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
            /*Glide.with(this)
                    .load(mPaths.get(0))
                    .centerCrop()
                    .placeholder(R.drawable.smsp_logo)
                    .into(uploadImageId);
            selectedImagePath = mPaths.get(0);*/
        }
    }
}