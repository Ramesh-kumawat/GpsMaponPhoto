package com.example.gpsmaponphoto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.jvm.internal.Intrinsics;

public class ExitActivity extends AppCompatActivity {

 ImageView one,two,three,four,five,exit_btn;
 LinearLayout ll_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        exit_btn = findViewById(R.id.exit_btn);
        ll_main = findViewById(R.id.ll_main);


        size();

        exit_btn.setOnClickListener(view -> finishAffinity());

        one.setOnClickListener(view -> {

            one.setImageResource(R.drawable.star_fill);
            two.setImageResource(R.drawable.star_unfill);
            three.setImageResource(R.drawable.star_unfill);
            four.setImageResource(R.drawable.star_unfill);
            five.setImageResource(R.drawable.star_unfill);
            redirect();
        });

        two.setOnClickListener(view -> {

            one.setImageResource(R.drawable.star_fill);
            two.setImageResource(R.drawable.star_fill);
            three.setImageResource(R.drawable.star_unfill);
            four.setImageResource(R.drawable.star_unfill);
            five.setImageResource(R.drawable.star_unfill);
            redirect();
        });

        three.setOnClickListener(view -> {

            one.setImageResource(R.drawable.star_fill);
            two.setImageResource(R.drawable.star_fill);
            three.setImageResource(R.drawable.star_fill);
            four.setImageResource(R.drawable.star_unfill);
            five.setImageResource(R.drawable.star_unfill);
            redirect();

        });

        four.setOnClickListener(view -> {

            one.setImageResource(R.drawable.star_fill);
            two.setImageResource(R.drawable.star_fill);
            three.setImageResource(R.drawable.star_fill);
            four.setImageResource(R.drawable.star_fill);
            five.setImageResource(R.drawable.star_unfill);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("market://details?id=", getPackageName()))));
            finishAffinity();

        });
        five.setOnClickListener(view -> {

            one.setImageResource(R.drawable.star_fill);
            two.setImageResource(R.drawable.star_fill);
            three.setImageResource(R.drawable.star_fill);
            four.setImageResource(R.drawable.star_fill);
            five.setImageResource(R.drawable.star_fill);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Intrinsics.stringPlus("market://details?id=", getPackageName()))));
            finishAffinity();
        });

    }

    private void size() {


            HelperResizer.getheightandwidth(this);

            HelperResizer.setSize(exit_btn,468,146);
            HelperResizer.setMargin(exit_btn,0,0,0,220);

            HelperResizer.setSize(one,84,84);
            HelperResizer.setMargin(one,0,0,50,0);

            HelperResizer.setSize(two,84,84);
            HelperResizer.setMargin(two,0,0,50,0);

            HelperResizer.setSize(three,84,84);
            HelperResizer.setMargin(three,0,0,50,0);

            HelperResizer.setSize(four,84,84);
            HelperResizer.setMargin(four,0,0,50,0);

            HelperResizer.setSize(five,84,84);

            HelperResizer.setSize(ll_main,1080,415);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                finishAffinity();
                return;
            }
            Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}