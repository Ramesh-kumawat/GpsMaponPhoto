package com.example.gpsmaponphoto;

import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import kotlin.jvm.internal.Intrinsics;


public class MainActivity extends AppCompatActivity  {

    ImageView start_btn,share_btn,privacy_btn,rate_btn;
     LocationManager locationManager;
    boolean isProviderEnabled;
     ProgressBar progressbar_main;
     Dialog dialog;
    ImageView one_rate,two_rate,three_rate,four_rate,five_rate,overlary_main;
    ImageView imageView4;
    LinearLayout ll_main_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        start_btn = findViewById(R.id.start_btn);
        share_btn = findViewById(R.id.share_btn);
        privacy_btn = findViewById(R.id.privacy_btn);
        rate_btn = findViewById(R.id.rate_btn);
        overlary_main = findViewById(R.id.overlary_main);
        progressbar_main = findViewById(R.id.progressbar_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_for_rate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(getDrawable(android.R.color.transparent));



        one_rate = dialog.findViewById(R.id.one_rate);
        two_rate = dialog.findViewById(R.id.two_rate);
        three_rate = dialog.findViewById(R.id.three_rate);
        four_rate = dialog.findViewById(R.id.four_rate);
        five_rate = dialog.findViewById(R.id.five_rate);
        ll_main_rate = dialog.findViewById(R.id.ll_main_rate);
        imageView4 = dialog.findViewById(R.id.imageView4);


        size();

        rate_btn.setOnClickListener(view -> {
            one_rate.setImageResource(R.drawable.star_unfill);
            two_rate.setImageResource(R.drawable.star_unfill);
            three_rate.setImageResource(R.drawable.star_unfill);
            four_rate.setImageResource(R.drawable.star_unfill);
            five_rate.setImageResource(R.drawable.star_unfill);
            dialog.show();



          one_rate.setOnClickListener(view1 -> {

              one_rate.setImageResource(R.drawable.star_fill);
              two_rate.setImageResource(R.drawable.star_unfill);
              three_rate.setImageResource(R.drawable.star_unfill);
              four_rate.setImageResource(R.drawable.star_unfill);
              five_rate.setImageResource(R.drawable.star_unfill);
              redirect();
          });

          two_rate.setOnClickListener(view12 -> {

              one_rate.setImageResource(R.drawable.star_fill);
              two_rate.setImageResource(R.drawable.star_fill);
              three_rate.setImageResource(R.drawable.star_unfill);
              four_rate.setImageResource(R.drawable.star_unfill);
              five_rate.setImageResource(R.drawable.star_unfill);
              redirect();
          });

          three_rate.setOnClickListener(view13 -> {

              one_rate.setImageResource(R.drawable.star_fill);
              two_rate.setImageResource(R.drawable.star_fill);
              three_rate.setImageResource(R.drawable.star_fill);
              four_rate.setImageResource(R.drawable.star_unfill);
              five_rate.setImageResource(R.drawable.star_unfill);

              redirect();

          });

          four_rate.setOnClickListener(view14 -> {

              one_rate.setImageResource(R.drawable.star_fill);
              two_rate.setImageResource(R.drawable.star_fill);
              three_rate.setImageResource(R.drawable.star_fill);
              four_rate.setImageResource(R.drawable.star_fill);
              five_rate.setImageResource(R.drawable.star_unfill);


             startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("market://details?id=", getPackageName()))));
             finishAffinity();

          });
          five_rate.setOnClickListener(view15 -> {

              one_rate.setImageResource(R.drawable.star_fill);
              two_rate.setImageResource(R.drawable.star_fill);
              three_rate.setImageResource(R.drawable.star_fill);
              four_rate.setImageResource(R.drawable.star_fill);
              five_rate.setImageResource(R.drawable.star_fill);



             startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("market://details?id=", getPackageName()))));
              finishAffinity();
          });


        });

        privacy_btn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,PrivacyActivity.class);
            startActivity(intent);
            privacy_btn.setEnabled(false);
            share_btn.setEnabled(false);
            rate_btn.setEnabled(false);

        });



        share_btn.setOnClickListener(view -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String app_url = "https://play.google.com/store/apps/details?id=com.example.gpsmaponphoto";
            shareIntent.putExtra(Intent.EXTRA_TEXT,app_url);
            startActivity(Intent.createChooser(shareIntent, "Share"));
            privacy_btn.setEnabled(false);
            share_btn.setEnabled(false);
            rate_btn.setEnabled(false);
        });

        start_btn.setOnClickListener(view -> {

            isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isProviderEnabled) {
                showSettingsAlert();
            } else {
                progressbar_main.setVisibility(View.VISIBLE);
                overlary_main.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this,GpsPhotoActivity.class);
                startActivity(intent);
                start_btn.setEnabled(false);
                privacy_btn.setEnabled(false);
                rate_btn.setEnabled(false);
                share_btn.setEnabled(false);
            }

        });


    }

    private void size() {
        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(start_btn,435,146);
        HelperResizer.setMargin(start_btn,0,40,0,0);

        HelperResizer.setSize(privacy_btn,91,91);
        HelperResizer.setMargin(privacy_btn,15,0,0,0);

        HelperResizer.setSize(rate_btn,91,91);
        HelperResizer.setMargin(rate_btn,0,50,0,0);

        HelperResizer.setSize(overlary_main,1080,1920,true);

        HelperResizer.setSize(share_btn,91,91);
        HelperResizer.setMargin(share_btn,0,0,15,0);

        HelperResizer.setSize(one_rate,84,84);
        HelperResizer.setMargin(one_rate,0,0,50,0);

        HelperResizer.setSize(two_rate,84,84);
        HelperResizer.setMargin(two_rate,0,0,50,0);

        HelperResizer.setSize(three_rate,84,84);
        HelperResizer.setMargin(three_rate,0,0,50,0);

        HelperResizer.setSize(four_rate,84,84);
        HelperResizer.setMargin(four_rate,0,0,50,0);

        HelperResizer.setSize(five_rate,84,84);

        HelperResizer.setSize(ll_main_rate,800,445);
        HelperResizer.setMargin(ll_main_rate,0,200,0,0);

        HelperResizer.setSize(imageView4,199,279);



    }

    public void showSettingsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS settings");
        builder.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        builder.setPositiveButton("Settings", (dialogInterface, i) -> startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 201));
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);

        if (i == 201) {
            isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isProviderEnabled) {
                startActivity(new Intent(this, GpsPhotoActivity.class));
            }
        }
    }

    @Override
    protected void onResume() {
        start_btn.setEnabled(true);
        privacy_btn.setEnabled(true);
        rate_btn.setEnabled(true);
        share_btn.setEnabled(true);
        overlary_main.setVisibility(View.INVISIBLE);
        super.onResume();
        progressbar_main.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this,ExitActivity.class);
        startActivity(intent);
    }

public  void redirect(){
    Intent intent = new Intent("android.intent.action.SEND");
    intent.setType("text/plain");
    intent.setPackage("com.google.android.gm");
    intent.putExtra("android.intent.extra.EMAIL", new String[]{"Ramesh.kumawat.beetonz@gmail.com"});
    intent.putExtra("android.intent.extra.SUBJECT", "Hello User");
    try {
        if (intent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(Intent.createChooser(intent, "Send email using..."), 1254);
        dialog.dismiss();
        finishAffinity();

            return;
        }
        Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
    } catch (ActivityNotFoundException unused) {
        Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
    }
}
}
