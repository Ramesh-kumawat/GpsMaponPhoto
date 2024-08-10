package com.example.gpsmaponphoto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashScreenActivity extends AppCompatActivity {

     ImageView progressBar;
     Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        Glide.with(this).load(R.drawable.loading).into(progressBar);
        size();
        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },5000);

    }
    public  void size(){

        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(progressBar,300,300);
        HelperResizer.setMargin(progressBar,0,0,0,120);


    }

}