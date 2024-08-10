package com.example.gpsmaponphoto;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsmaponphoto.Adapters.StickerViewAdapter;
import com.example.gpsmaponphoto.Models.StickerModel;
import com.example.gpsmaponphoto.Utils.AllLocation;
import com.example.gpsmaponphoto.Utils.Storage;
import com.kwabenaberko.openweathermaplib.constant.Languages;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class GalleryViewActivity extends AppCompatActivity
        implements StickerViewAdapter.onpositionselect,View.OnTouchListener
{

    ImageView show_img,back_btn_gallery_view,save_image;
    ImageView button;
    RecyclerView recycleview_stickers;
    StickerViewAdapter stickerViewAdapter;
    RelativeLayout sticker_img;
    ConstraintLayout total_img;
    ViewGroup parent;

    PointF DownPT = null;
    PointF StartPT = null;

    ArrayList<StickerModel> stickerModels = new ArrayList<>();
    Dialog dialog;
    public static int selected = -1;

    View inflatedView;
    AllLocation mGPS;
     ImageView unSave_btn_dialog,Save_btn_dialog;
     int height;
     int width;
     SharedPreferences preferences1;
     ImageView photo;
     LinearLayout main_box;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        show_img = findViewById(R.id.show_img);
        back_btn_gallery_view = findViewById(R.id.back_btn_gallery_view);
        button = findViewById(R.id.button);
        sticker_img = findViewById(R.id.sticker_img);
        save_image = findViewById(R.id.save_image);
        total_img = findViewById(R.id.total_img);


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_for_save);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


        Save_btn_dialog = dialog.findViewById(R.id.save);
        unSave_btn_dialog = dialog.findViewById(R.id.unsave);
        photo = dialog.findViewById(R.id.photo);
        main_box = dialog.findViewById(R.id.main_box);


        preferences1 = getSharedPreferences("map_type",MODE_PRIVATE);

        size();


        DownPT = new PointF();
        StartPT = new PointF();

        total_img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                total_img.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                width = total_img.getMeasuredWidth();
                 height = total_img.getMeasuredHeight();
            }
        });

        sticker_img.setOnTouchListener(this);
        recycleview_stickers = findViewById(R.id.recycleview_stickers);

            mGPS = new AllLocation(this);


        mGPS.AddNewView(stickerModels);

         stickerViewAdapter = new StickerViewAdapter(stickerModels,this,this,1);
         recycleview_stickers.setAdapter(stickerViewAdapter);


        Intent intent = getIntent();
         String i = intent.getStringExtra("imgPath");
        Log.d("TAG", "onCreate: "+i);
        File imgFile = new  File(i);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        if(myBitmap != null){
            int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
            show_img.setImageBitmap(scaled);
        }

        button.setOnClickListener(view -> {

            recycleview_stickers.setVisibility(View.VISIBLE);
            sticker_img.setVisibility(View.INVISIBLE);

            if(sticker_img.getVisibility() != View.VISIBLE) {
              if(inflatedView != null){
                 parent = (ViewGroup) inflatedView.getParent();
                if (parent != null) {
                    parent.removeView(inflatedView);
                }}
            }
        });


        save_image.setOnClickListener(view -> {

            dialog.show();

              Save_btn_dialog.setOnClickListener(view1 -> {
                  if(recycleview_stickers.getVisibility() == View.VISIBLE) {
                      recycleview_stickers.setVisibility(View.GONE);
                  }
                      Bitmap access = getBitmapFromView(total_img);
                      String CreateFolder = Storage.CreateFolder(getResources().getString(R.string.app_name),"Images" );
                      Storage.saveimage(getApplicationContext(), access, CreateFolder, (new Random().nextInt() + R.string.app_name) + ".jpeg");
                      dialog.dismiss();
                  Toast.makeText(GalleryViewActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();

              });

              unSave_btn_dialog.setOnClickListener(view12 -> dialog.dismiss());


        });

        back_btn_gallery_view.setOnClickListener(view -> onBackPressed());
    }

    private void size() {
        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(back_btn_gallery_view,60,65);
        HelperResizer.setPadding(back_btn_gallery_view,10,10,10,10);
        HelperResizer.setMargin(back_btn_gallery_view,70,0,0,0);

          HelperResizer.setSize(save_image,203,102);
        HelperResizer.setMargin(save_image,0,0,60,0);

          HelperResizer.setSize(button,154,154);
        HelperResizer.setMargin(button,0,0,0,95);

          HelperResizer.setSize(Save_btn_dialog,320,115);

          HelperResizer.setSize(unSave_btn_dialog,320,115);
        HelperResizer.setMargin(unSave_btn_dialog,0,0,50,0);

        HelperResizer.setSize(photo,89,82);
        HelperResizer.setMargin(photo,0,40,0,0);

        HelperResizer.setSize(main_box,858,570);

    }

    public static Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    @SuppressLint("InflateParams")
    @Override
    public void postionOfItem(int position) {

        selected = position;
       stickerViewAdapter.notifyDataSetChanged();
       recycleview_stickers.setVisibility(View.INVISIBLE);
        sticker_img.setVisibility(View.VISIBLE);

        switch (position) {
            case 0:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_1, null, false);
                break;
            case 1:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_2, null, false);
                break;
            case 2:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_3, null, false);
                break;
            case 3:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_4, null, false);
                break;
            case 4:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_5, null, false);
                break;
            case 5:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_6, null, false);
                break;
            case 6:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_7, null, false);
                break;
            case 7:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_8, null, false);
                break;
            case 8:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_9, null, false);
                break;
            case 9:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_10, null, false);
                break;
            case 10:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_11, null, false);
                break;
            case 11:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_12, null, false);
                break;
            case 12:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_13, null, false);
                break;
            case 13:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_14, null, false);
                break;
            case 14:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_15, null, false);
                break;
            case 15:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_16, null, false);
                break;
            case 16:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_17, null, false);
                break;
            case 17:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_18, null, false);
                break;
            case 18:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_19, null, false);
                break;
            case 19:
                sticker_img.removeView(inflatedView);
                inflatedView = LayoutInflater.from(this).inflate(R.layout.template_20, null, false);
                break;

        }
        sticker_img.addView(inflatedView);


        TextView txt_full_add = inflatedView.findViewById(R.id.txt_full_add);
        TextView txt_add = inflatedView.findViewById(R.id.txt_add);
        TextView txt_lat_log = inflatedView.findViewById(R.id.txt_lat_log);
        TextView txt_time = inflatedView.findViewById(R.id.txt_time);
        TextView txt_add_1 = inflatedView.findViewById(R.id.txt_add_1);


        txt_add_1.setText(mGPS.getcountry());
        txt_add.setText(mGPS.getCity());

        txt_full_add.setText(mGPS.getAddressLine());
        txt_lat_log.setText("let : "+new DecimalFormat("##.##").format(mGPS.getLatitude())+" log : "+new DecimalFormat("##.##").format(mGPS.getLongitude()));
        CallWeather();


        int address = preferences1.getInt("address",1);
        int d_address = preferences1.getInt("d_address",1);
        int coordinate = preferences1.getInt("coordinate",1);

        int time = preferences1.getInt("time",1);

        if(txt_add.getVisibility() != View.GONE) {
            txt_add.setVisibility(address == 1 ? View.VISIBLE : View.INVISIBLE);
            txt_add_1.setVisibility(address == 1 ? View.VISIBLE : View.INVISIBLE);
        }
        if(txt_full_add.getVisibility() != View.GONE) {
            txt_full_add.setVisibility(d_address == 1 ? View.VISIBLE : View.INVISIBLE);
        }
        if(txt_lat_log.getVisibility() != View.GONE) {
            txt_lat_log.setVisibility(coordinate == 1 ? View.VISIBLE : View.INVISIBLE);
        }
        if(txt_time.getVisibility() != View.GONE) {
            txt_time.setVisibility(time == 1 ? View.VISIBLE : View.INVISIBLE);
        }

    }
    public void CallWeather() {
        OpenWeatherMapHelper openWeatherMapHelper = new OpenWeatherMapHelper("0cbb9115f01f229ef6156413d7eb8fb6");
        openWeatherMapHelper.setUnits(Units.IMPERIAL);
        openWeatherMapHelper.setLanguage(Languages.ENGLISH);
        openWeatherMapHelper.getCurrentWeatherByCityName(mGPS.getCity(), new CurrentWeatherCallback() {
            public void onFailure(Throwable th) {
                Log.d("TAG", "onFailure: ");
            }

            public void onSuccess(CurrentWeather currentWeather) {
                TextView txt_temp = inflatedView.findViewById(R.id.txt_temp);
                TextView txt_humidity = inflatedView.findViewById(R.id.txt_humidity);


                int temperature = preferences1.getInt("temperature",1);
                int weather = preferences1.getInt("weather",1);

                if(txt_temp.getVisibility() != View.GONE) {
                    txt_temp.setVisibility(temperature == 1 ? View.VISIBLE : View.INVISIBLE);
                }
                if(txt_humidity.getVisibility() != View.GONE) {
                    txt_humidity.setVisibility(weather == 1 ? View.VISIBLE : View.INVISIBLE);
                }
                txt_temp.setText("" + new DecimalFormat("##").format(currentWeather.getMain().getTempMax()) + "°F");
                Log.d("TAG", "onSuccess: 1 "+ new DecimalFormat("##").format(currentWeather.getMain().getTempMax()) + "°F");
                Log.d("TAG", "onSuccess: 2 "+currentWeather.getMain().getHumidity());

                txt_humidity.setText("" + currentWeather.getMain().getHumidity());

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eid = event.getAction();
        switch (eid) {
            case MotionEvent.ACTION_MOVE:
                    PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                    int imgWidth = v.getWidth() / 2;
                    int imgHeight = v.getHeight() / 2;


                    if (StartPT.x + mv.x + imgWidth > 250 && StartPT.x + mv.x + imgWidth < width - 250) {
                        v.setX((int) (StartPT.x + mv.x));
                    }
                    if (StartPT.y + mv.y + imgHeight > 100 && StartPT.y + mv.y + imgHeight < height - 100) {
                        v.setY((int) (StartPT.y + mv.y));
                    }
                    StartPT = new PointF(v.getX(), v.getY());

                break;
            case MotionEvent.ACTION_DOWN:

                    DownPT.x = event.getX();
                    DownPT.y = event.getY();
                    StartPT = new PointF(v.getX(), v.getY());
                    Log.e("get points : ", v.getX() + "" + v.getY());

                break;
        }
        return true;
    }
}