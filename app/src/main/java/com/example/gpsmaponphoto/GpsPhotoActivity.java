package com.example.gpsmaponphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.gpsmaponphoto.Adapters.pictureFolderAdapter;
import com.example.gpsmaponphoto.Models.StickerModel;
import com.example.gpsmaponphoto.Models.imageFolder;
import com.example.gpsmaponphoto.Utils.AllLocation;
import com.example.gpsmaponphoto.Utils.Storage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kwabenaberko.openweathermaplib.constant.Languages;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.Mode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class GpsPhotoActivity extends AppCompatActivity  implements LocationListener,pictureFolderAdapter.itemClickListener,View.OnTouchListener {


    CameraView camera_view;
    ImageView face_view,hdr_img,imgCollation,flash_light,timer_img,setting_viewpager,imageView2,gallery_img_view,map_btn,btnCapture,off_text,five_sec_text,ten_sec_text;
    LinearLayout ll_timer;
    TextView timer_text_view;
    ConstraintLayout cl_gps_map;
    View gps_photo_map;
    int position,edit;
    RelativeLayout rl_stickers;
    public static float posofx;
    public static float posofy;
    ArrayList<StickerModel> sticker = new ArrayList<>();
    Bitmap mapbitmap;
    RecyclerView.Adapter folderAdapter;
    ImageView header_main;
    String lastimagepath;
    LocationManager locationManager;
    GoogleMap gmap;
    AllLocation mGPS;
    Double longitude_map,latitude_map;
    private CardView widghtsHolders;
     int width,height;
    View inflatedView;
    ImageView mapimage;
    PointF DownPT = null;
    PointF StartPT = null;
    private boolean check = true;
    SharedPreferences preferences1;
    int height1,width1;
    int heightofscreen,widthofscreen;
    SharedPreferences preferences;
     ConstraintLayout total_img_main;
     CardView relative_fragment_2;
     int imgWidth,imgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_photo);

         preferences = getSharedPreferences("timer",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        preferences1 = getSharedPreferences("map_type",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        heightofscreen = displayMetrics.heightPixels;
         widthofscreen = displayMetrics.widthPixels;

        editor1.putInt("edit",0);
        editor1.apply();

        initialize();
        Thread thread = new Thread(runnable);
        thread.start();

        size();
        DownPT = new PointF();
        StartPT = new PointF();

        setting_viewpager.setOnClickListener(view -> {
            Intent intent = new Intent(GpsPhotoActivity.this, StickerSettingActivity.class);
            startActivity(intent);
        });



        imgCollation.setOnClickListener(view -> {
              Intent intent = new Intent(GpsPhotoActivity.this,collectionActivity.class);
              intent.putExtra("lastimagepath",lastimagepath);
              startActivity(intent);
        });

        off_text.setOnClickListener(view -> {
            editor.putInt("time",0);
            editor.apply();
            ll_timer.setVisibility(View.INVISIBLE);
            check = true;
            off_text.setImageResource(R.drawable.off_press);
            five_sec_text.setImageResource(R.drawable.five_unpress);
            ten_sec_text.setImageResource(R.drawable.ten_unpress);
            timer_img.setImageResource(R.drawable.timer_off);
        });
        five_sec_text.setOnClickListener(view -> {
            editor.putInt("time",1);
            editor.apply();
            ll_timer.setVisibility(View.INVISIBLE);
            check = true;
            off_text.setImageResource(R.drawable.off_unpress);
            five_sec_text.setImageResource(R.drawable.five_press);
            ten_sec_text.setImageResource(R.drawable.ten_unpress);
            timer_img.setImageResource(R.drawable.timer_off);

        });
        ten_sec_text.setOnClickListener(view -> {
            editor.putInt("time",2);
            editor.apply();
            ll_timer.setVisibility(View.INVISIBLE);
            check = true;
            off_text.setImageResource(R.drawable.off_unpress);
            five_sec_text.setImageResource(R.drawable.five_unpress);
            ten_sec_text.setImageResource(R.drawable.ten_press);
            timer_img.setImageResource(R.drawable.timer_off);
        });


        timer_img.setOnClickListener(view -> {

            if(check){
                ll_timer.setVisibility(View.VISIBLE);
                timer_img.setImageResource(R.drawable.timer_on);
                check = false;
            }else{
                ll_timer.setVisibility(View.GONE);
                timer_img.setImageResource(R.drawable.timer_off);
                check = true;
            }


        });
        face_view.setOnClickListener(view -> {

            if(camera_view.getFacing() == Facing.FRONT){
                camera_view.setFacing(Facing.BACK);
            }else{
                camera_view.setFacing(Facing.FRONT);

            }


        });
        flash_light.setOnClickListener(view -> {

        if(camera_view.getFlash() == Flash.OFF) {
            flash_light.setImageResource(R.drawable.flash_press);
            camera_view.setFlash(Flash.TORCH);
        }else {
            flash_light.setImageResource(R.drawable.flash_unpress);
            camera_view.setFlash(Flash.OFF);

        }
        });

        hdr_img.setOnClickListener(view -> {

            if(camera_view.getHdr() == Hdr.OFF){
                camera_view.setHdr(Hdr.ON);
                hdr_img.setImageResource(R.drawable.hdr_on);
            }else{
                camera_view.setHdr(Hdr.OFF);
                hdr_img.setImageResource(R.drawable.hdr_off);
            }


        });
        cl_gps_map.setOnClickListener(view -> ll_timer.setVisibility(View.INVISIBLE));


        btnCapture.setOnClickListener(view -> {

           int time_camera =  preferences.getInt("time",0);
            Log.d("TAG", "onClick: "+time_camera);

            switch (time_camera)
            {
                case 0:
                    Log.d("TAG", "onClick: 0");

                    if(position == 45){
                        GoogleMap.SnapshotReadyCallback callback = snapshot -> {
                            mapbitmap = snapshot;
                            mapimage.setVisibility(View.VISIBLE);
                            relative_fragment_2.setVisibility(View.INVISIBLE);
                            mapimage.setImageBitmap(getRoundedCornerBitmap(mapbitmap,30));

                         camera_view.takePictureSnapshot();
                        };
                        int map = preferences1.getInt("map", 1);
                        if (map == 1) {
                            if (gmap != null) {
                                gmap.snapshot(callback);

                            }
                        }else{
                            camera_view.takePictureSnapshot();
                        }


                    }else {

                        GoogleMap.SnapshotReadyCallback r6 = bitmap -> {
                            mapbitmap = bitmap;
                            int mapwidth = 200;
                            int mapheight = 200;
                            assert mapbitmap != null;
                            mapbitmap = HelperResizer.bitmapResize(mapbitmap, mapwidth, mapheight);
                        camera_view.takePicture();
                        };


                        int map = preferences1.getInt("map", 1);
                        if (map == 1) {
                            if (gmap != null) {

                                gmap.snapshot(r6);
                            }
                        }
                        camera_view.takePictureSnapshot();
                    }
                    Toast.makeText(this, "Capture Successfully", Toast.LENGTH_SHORT).show();
                    btnCapture.setEnabled(true);
                    break;

                case 1:
                    Log.d("TAG", "onClick: 1");
                    btnCapture.setEnabled(false);
                    timer_text_view.setText("5");
                    timer_text_view.setVisibility(View.VISIBLE);
                    new CountDownTimer(5000, 1000){
                        public void onTick(long millisUntilFinished){
                            timer_text_view.setText(""+millisUntilFinished / 1000);
                        }
                        public  void onFinish(){
                            timer_text_view.setVisibility(View.INVISIBLE);
                             if(position == 45){
                        GoogleMap.SnapshotReadyCallback callback = snapshot -> {
                            mapbitmap = snapshot;
                            mapimage.setVisibility(View.VISIBLE);
                            relative_fragment_2.setVisibility(View.INVISIBLE);
                            mapimage.setImageBitmap(getRoundedCornerBitmap(mapbitmap,30));

                         camera_view.takePictureSnapshot();
                        };
                        int map = preferences1.getInt("map", 1);
                        if (map == 1) {
                            if (gmap != null) { gmap.snapshot(callback); }
                        }else{ camera_view.takePictureSnapshot(); }
                    }else {
              GoogleMap.SnapshotReadyCallback r6 = bitmap -> {
                  mapbitmap = bitmap;
                  int mapwidth = 200;
                  int mapheight = 200;
                  assert mapbitmap != null;
                  mapbitmap = HelperResizer.bitmapResize(mapbitmap, mapwidth, mapheight);
                  camera_view.takePicture();
              };
                            int map = preferences1.getInt("map",0);
                            if (map == 1 ) {
                                if(gmap != null) {
                                    gmap.snapshot(r6);
                                }
                            }
                            camera_view.takePictureSnapshot();


                        }
                            Toast.makeText(GpsPhotoActivity.this, "Capture Successfully", Toast.LENGTH_SHORT).show();
                            btnCapture.setEnabled(true);
                        }
                    }.start();
                    break;
                case 2:


                    Log.d("TAG", "onClick: 2");
                    btnCapture.setEnabled(false);

                    timer_text_view.setText("10");
                    timer_text_view.setVisibility(View.VISIBLE);
                    new CountDownTimer(10000, 1000){
                        public void onTick(long millisUntilFinished){
                            timer_text_view.setText(""+millisUntilFinished / 1000);
                        }
                        public  void onFinish(){
                            timer_text_view.setVisibility(View.INVISIBLE);
                            camera_view.setMode(Mode.PICTURE);
                       if(position == 45){
                        GoogleMap.SnapshotReadyCallback callback = snapshot -> {
                            mapbitmap = snapshot;
                            mapimage.setVisibility(View.VISIBLE);
                            relative_fragment_2.setVisibility(View.INVISIBLE);
                            mapimage.setImageBitmap(getRoundedCornerBitmap(mapbitmap,30));
                         camera_view.takePictureSnapshot();
                        };
                        int map = preferences1.getInt("map", 1);
                        if (map == 1) {
                            if (gmap != null) {
                                gmap.snapshot(callback);

                            }
                        }else{
                            camera_view.takePictureSnapshot();
                        }


                    }else {
                            GoogleMap.SnapshotReadyCallback callback = bitmap -> {
                                mapbitmap = bitmap;
                                int mapwidth = 200;
                                int mapheight = 200;
                                assert mapbitmap != null;
                                mapbitmap = HelperResizer.bitmapResize(mapbitmap, mapwidth, mapheight);
                                camera_view.takePicture();
                            };
                            int map = preferences1.getInt("map",0);
                            if (map == 1 ) {
                                if(gmap != null) {
                                    gmap.snapshot(callback);
                                }
                            }
                            camera_view.takePictureSnapshot();

                        }
                            Toast.makeText(GpsPhotoActivity.this, "Capture Successfully", Toast.LENGTH_SHORT).show();
                            btnCapture.setEnabled(true);
                        }
                    }.start();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + time_camera);
            }


        });

        map_btn.setOnClickListener(view -> {

            Intent intent = new Intent(GpsPhotoActivity.this,map_activity.class);
            startActivity(intent);
            map_btn.setEnabled(false);

        });
        gallery_img_view.setOnClickListener(view -> {
            if (permission()) {
                GalleryFolderDialog();

            } else {
                ActivityCompat.requestPermissions(GpsPhotoActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.ACCESS_COARSE_LOCATION"}, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            }
        });



    }
    Runnable runnable = new Runnable(){
        public void run() {

            runOnUiThread(() -> {
                camera_view.setLifecycleOwner(GpsPhotoActivity.this);
                mGPS = new AllLocation(GpsPhotoActivity.this);
                mGPS.AddNewView(sticker);

                boolean connection = connectivity();

                if(!connection){
                    Toast.makeText(GpsPhotoActivity.this, "please Enable Mobile data/Wifi", Toast.LENGTH_SHORT).show();

                }
            });




            camera_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    camera_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    width = camera_view.getMeasuredWidth();
                    height = camera_view.getMeasuredHeight();
                }
            });

            camera_view.addCameraListener(new CameraListener() {

                @Override
                public void onPictureTaken(@NonNull PictureResult result) {

                    byte[] data = result.getData();
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(data, 0, data.length);
                    height1 = decodeByteArray.getHeight();
                    width1 = decodeByteArray.getWidth();

                    Bitmap bitmapResize = HelperResizer.bitmapResize(decodeByteArray, width,height);
                    Log.d("TAG", "onPictureTaken: "+mapbitmap);
                    if(mapbitmap != null){
                        int map = preferences1.getInt("map",1);
                        if(map == 1){
                            if(position == 45){
                                Bitmap mergeToPin = mergemapPin(bitmapResize, getRoundedCornerBitmap(mapbitmap,30) , 1);
                                if (mergeToPin != null) {
                                    Bitmap access = getBitmapFromView(total_img_main);
                                    Bitmap mergeToPin2 = mergeToPin( mergeToPin, access, 2);
                                    String createFolder = Storage.CreateFolder(getResources().getString(R.string.app_name), "Images");
                                    lastimagepath = Storage.saveimage(getApplicationContext(), mergeToPin2, createFolder, (new Random().nextInt() + R.string.app_name) + ".jpeg");
                                    Glide.with(GpsPhotoActivity.this).load(lastimagepath).centerCrop().transform(new CenterCrop(),new RoundedCorners(14)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgCollation);
                                }

                                relative_fragment_2.setVisibility(View.VISIBLE);
                                mapimage.setVisibility(View.INVISIBLE);
                            }else{

                            Bitmap mergeToPin = mergeToPin(bitmapResize, getRoundedCornerBitmap(mapbitmap,30) , 1);
                            if (mergeToPin != null) {
                                Bitmap access = getBitmapFromView(total_img_main);

                                Bitmap mergeToPin2 = mergeToPin( mergeToPin, access, 2);
                                String createFolder = Storage.CreateFolder(getResources().getString(R.string.app_name), "Images");
                                lastimagepath = Storage.saveimage(getApplicationContext(), mergeToPin2, createFolder, (new Random().nextInt() + R.string.app_name) + ".jpeg");
                                Glide.with(GpsPhotoActivity.this).load(lastimagepath).centerCrop().transform(new CenterCrop(),new RoundedCorners(14)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgCollation);
                            }
                            }
                        }else {
                            Bitmap access = getBitmapFromView(total_img_main);
                            Bitmap mergeToPin2  = mergeToPin( bitmapResize, access, 2);
                            String createFolder = Storage.CreateFolder(getResources().getString(R.string.app_name), "Images");
                            lastimagepath = Storage.saveimage(getApplicationContext(),mergeToPin2, createFolder, (new Random().nextInt() + R.string.app_name) + ".jpeg");
                            Glide.with(GpsPhotoActivity.this).load(lastimagepath).transform(new CenterCrop(),new RoundedCorners(14)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgCollation);

                        }
                    }else{
                        Bitmap access = getBitmapFromView(total_img_main);
                        Bitmap mergeToPin2 = mergeToPin(bitmapResize, access, 2);
                        String createFolder = Storage.CreateFolder(getResources().getString(R.string.app_name), "Images");
                        lastimagepath = Storage.saveimage(getApplicationContext(), mergeToPin2,createFolder, (new Random().nextInt() + R.string.app_name) + ".jpeg");
                        Glide.with(GpsPhotoActivity.this).load(lastimagepath).transform(new CenterCrop(),new RoundedCorners(14)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgCollation);
                    }


                }
            });


        }
    };

    private void size() {

        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(setting_viewpager,90,90);
        HelperResizer.setMargin(setting_viewpager,0,0,75,30);

        HelperResizer.setSize(imgCollation,90,90);
        HelperResizer.setMargin(imgCollation,75,0,0,30);

        HelperResizer.setSize(map_btn,90,90);
        HelperResizer.setMargin(map_btn,140,0,0,0);

        HelperResizer.setSize(btnCapture,189,189);
        HelperResizer.setMargin(btnCapture,0,20,0,0);

        HelperResizer.setSize(gallery_img_view,90,90);
        HelperResizer.setMargin(gallery_img_view,0,0,140,0);

        HelperResizer.setSize(flash_light,29,57);
        HelperResizer.setMargin(flash_light,160,70,0,0);

        HelperResizer.setSize(hdr_img,103,56);
        HelperResizer.setMargin(hdr_img,170,0,0,0);

        HelperResizer.setSize(timer_img,50,57);
        HelperResizer.setMargin(timer_img,170,0,0,0);

        HelperResizer.setSize(face_view,57,56);
        HelperResizer.setMargin(face_view,170,0,0,0);

        HelperResizer.setSize(imageView2,1080,307,true);

        HelperResizer.setSize(ll_timer,732,155);
        HelperResizer.setMargin(ll_timer,0,30,0,0);


        HelperResizer.setSize(off_text,244,155);
        HelperResizer.setMargin(off_text,1,0,0,0);


        HelperResizer.setSize(five_sec_text,244,155);

       HelperResizer.setSize(header_main,1080,215,true);


        HelperResizer.setSize(ten_sec_text,244,155);
        HelperResizer.setMargin(ten_sec_text,0,0,1,0);

         HelperResizer.setSize(widghtsHolders,298,226);
         HelperResizer.setMargin(widghtsHolders,0,308,50,0);



    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {
        Intent move = new Intent(GpsPhotoActivity.this,GalleryActivity.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);
        startActivity(move);
    }

    private void GalleryFolderDialog() {

        @SuppressLint("InflateParams") View bottomSheet = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog,null);
         BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.setCanceledOnTouchOutside(true);


        ImageView back = bottomSheetDialog.findViewById(R.id.img_back);
        RecyclerView folderRecycler = bottomSheetDialog.findViewById(R.id.recycle_images);
        ArrayList<imageFolder> folds = getPicturePaths();


        assert back != null;
        HelperResizer.setSize(back,60,65);
        HelperResizer.setPadding(back,10,10,10,10);
        HelperResizer.setMargin(back,70,0,0,0);
        if(folds.isEmpty()){
            Log.d("TAG", "GalleryFolderDialog:  ");
        }else{
            folderAdapter = new pictureFolderAdapter(folds,GpsPhotoActivity.this,this);
            assert folderRecycler != null;
            folderRecycler.setAdapter(folderAdapter);



        }
        Objects.requireNonNull(back).setOnClickListener(view -> bottomSheetDialog.dismiss());


        bottomSheetDialog.show();
    }


    private void initialize() {
        face_view = findViewById(R.id.face_view);
        camera_view = findViewById(R.id.camera_view);
        flash_light = findViewById(R.id.flash_light);
        hdr_img = findViewById(R.id.hdr_img);
        timer_img = findViewById(R.id.timer_img);
        ll_timer = findViewById(R.id.ll_timer);
        btnCapture = findViewById(R.id.btnCapture);
        imageView2 = findViewById(R.id.imageView2);
        header_main = findViewById(R.id.header_main);

         gps_photo_map = findViewById(R.id.gps_photo_map);
        map_btn = findViewById(R.id.map_btn);
        off_text = findViewById(R.id.off_text);
        rl_stickers = findViewById(R.id.rl_stickers);
        total_img_main = findViewById(R.id.total_img_main);
        five_sec_text = findViewById(R.id.five_sec_text);
        ten_sec_text = findViewById(R.id.ten_sec_text);
        cl_gps_map = findViewById(R.id.cl_gps_map);
        timer_text_view = findViewById(R.id.timer_text_view);
        gallery_img_view = findViewById(R.id.gallery_img_view);
        imgCollation = findViewById(R.id.img_collation);
        setting_viewpager = findViewById(R.id.setting_viewpager);
        widghtsHolders = findViewById(R.id.relative_fragment_1);



    }

    public Bitmap mergeToPin( Bitmap bitmap, Bitmap bitmap2, int i) {

        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);

        canvas.drawBitmap(bitmap, 0.0f, 0.0f,   null);

        if (i == 1) {

            canvas.drawBitmap(bitmap2,  ((bitmap.getWidth() - 200) - 30), 150f,  null);

        } else {

            float f = posofx;
            if (f == 0.0f && posofy == 0.0f) {
                canvas.drawBitmap(bitmap2, (float) ((canvas.getWidth() - bitmap2.getWidth()) / 2),  (canvas.getHeight() - bitmap2.getHeight()),  null);
            } else {
                canvas.drawBitmap(bitmap2, f, posofy,  null);
            }
        }
        return createBitmap;
    }
    public Bitmap mergemapPin( Bitmap bitmap, Bitmap bitmap2, int i) {
        mapimage.setImageBitmap(bitmap2);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);


        canvas.drawBitmap(bitmap, 0.0f, 0.0f,   null);


        if(i != 1) {
            float f = posofx;
            if (f == 0.0f && posofy == 0.0f) {
                canvas.drawBitmap(bitmap2, (float) ((canvas.getWidth() - bitmap2.getWidth()) / 2),  (canvas.getHeight() - bitmap2.getHeight()),  null);
            } else {
                canvas.drawBitmap(bitmap2, f, posofy,  null);
            }
        }
        return createBitmap;
    }
    public static Bitmap getBitmapFromView(View view) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());

        view.draw(canvas);
        return createBitmap;
    }


    public boolean permission() {
        return (((ContextCompat.checkSelfPermission(GpsPhotoActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(GpsPhotoActivity.this, "android.permission.CAMERA")) + ContextCompat.checkSelfPermission(GpsPhotoActivity.this, "android.permission.RECORD_AUDIO")) + ContextCompat.checkSelfPermission(GpsPhotoActivity.this, "android.permission.ACCESS_FINE_LOCATION")) + ContextCompat.checkSelfPermission(GpsPhotoActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED;
    }

    private ArrayList<imageFolder> getPicturePaths(){

        ArrayList<imageFolder> picFolders = new ArrayList<>();

        ArrayList<String> picPaths = new ArrayList<>();

        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                imageFolder folds = new imageFolder();
                assert cursor != null;
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);
                    folds.addpics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d("TAG", "getPicturePaths: ");
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }
        return picFolders;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("InflateParams")
    @Override
    protected void onResume() {
        super.onResume();
        map_btn.setEnabled(true);
       lastimagepath = HelperResizer.getImages(this, Storage.CreateFolder(getResources().getString(R.string.app_name), "Images"));
       Glide.with(this).load(lastimagepath).transform(new CenterCrop(),new RoundedCorners(14)).placeholder( R.color.black).into(imgCollation);


        if (permission()) {
            boolean z = true;
            if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED ) {
                z = false;
            }
            if (z) {

                    mGPS = new AllLocation(this);

            }
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            longitude_map =  mGPS.getLongitude();
            latitude_map =mGPS.getLatitude();
             position = preferences1.getInt("MainLayout",45);


                switch (position) {
                    case 0:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_1, null, false);
                        break;
                 case 1:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_2, null, false);
                        break;
                 case 2:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_3, null, false);
                        break;
                 case 3:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_4, null, false);
                        break;
                 case 4:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_5, null, false);
                        break;
                 case 5:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_6, null, false);
                        break;
                 case 6:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_7, null, false);
                        break;
                 case 7:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_8, null, false);
                        break;
                 case 8:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_9, null, false);
                        break;
                 case 9:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_10, null, false);
                        break;
                  case 10:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_11, null, false);
                        break;
                  case 11:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_12, null, false);
                        break;
                  case 12:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_13, null, false);
                        break;
                  case 13:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_14, null, false);
                        break;
                  case 14:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_15, null, false);
                        break;
                  case 15:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_16, null, false);
                        break;
                  case 16:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_17, null, false);
                        break;
                  case 17:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_18, null, false);
                        break;
                 case 18:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_19, null, false);
                        break;
                 case 19:
                        rl_stickers.removeView(inflatedView);
                        inflatedView = LayoutInflater.from(this).inflate(R.layout.template_20, null, false);
                        break;


                    case 45:
                        rl_stickers.removeView(inflatedView);
                        if (inflatedView != null) {
                            ViewGroup parent = (ViewGroup) inflatedView.getParent();
                            if (parent != null)
                                parent.removeView(inflatedView);
                        }
                        try {
                            inflatedView = LayoutInflater.from(this).inflate(R.layout.two_widgets, null, false);
                            mapimage = inflatedView.findViewById(R.id.mapimage);
                            relative_fragment_2 = inflatedView.findViewById(R.id.relative_fragment_2);

                        } catch (InflateException e) {
                            Log.d("TAG", "onResume: view is already added");
                            /* map is already there, just return view as it is */
                        }

                        break;

                }
                rl_stickers.removeView(inflatedView);
                rl_stickers.addView(inflatedView);

//            }

             edit = preferences1.getInt("edit",0);
            if(edit == 1) {
                rl_stickers.setOnTouchListener(this);
            }

            if(position == 45){

                SupportMapFragment supportMapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gps_photo_map_1);
                assert supportMapFragment1 != null;
                supportMapFragment1.getMapAsync(googleMap -> {

                    gmap = googleMap;
                    gmap.clear();

                    int height = 50;
                    int width = 30;
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.locationpin);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    if(latitude_map != null) {

                        LatLng sydney = new LatLng(latitude_map, longitude_map);
                        gmap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        SharedPreferences sharedPreferences = getSharedPreferences("map_type", Context.MODE_PRIVATE);
                        int i = sharedPreferences.getInt("type", 1);
                        switch (i) {
                            case 0:
                                gmap.setMapType(GoogleMap.MAP_TYPE_NONE);

                                break;
                            case 1:
                                gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                break;
                            case 2:
                                gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                                break;
                            case 3:
                                gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 4:
                                gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;

                        }
                        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mGPS.getLatitude(), mGPS.getLongitude()), 14.0f));
                    }
                });

            } else {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gps_photo_map);
                assert supportMapFragment != null;

                supportMapFragment.getMapAsync(googleMap -> {

                    int height = HelperResizer.setHeight(87);
                    int width = HelperResizer.setWidth(57);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.locationpin);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    gmap = googleMap;
                    gmap.clear();
                  if(latitude_map != null) {
                      LatLng sydney = new LatLng(latitude_map, longitude_map);
                      gmap.addMarker(new MarkerOptions()
                              .position(sydney).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                      SharedPreferences sharedPreferences = getSharedPreferences("map_type", Context.MODE_PRIVATE);
                      int i = sharedPreferences.getInt("type", 1);
                      switch (i) {
                          case 0:
                              gmap.setMapType(GoogleMap.MAP_TYPE_NONE);

                              break;
                          case 1:
                              gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                              break;
                          case 2:
                              gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                              break;
                          case 3:
                              gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                              break;
                          case 4:
                              gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                              break;
                      }
                      gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mGPS.getLatitude(), mGPS.getLongitude()), 14.0f));
                  }
                });
            }
            Log.d("TAG", "onResume: 1"+mGPS.getAddressLine());
            Log.d("TAG", "onResume: 2"+mGPS.getCity());
            Log.d("TAG", "onResume: 3"+mGPS.getcountry());
            Log.d("TAG", "onResume: 4"+mGPS.getLatitude());
            Log.d("TAG", "onResume: 5"+mGPS.getLongitude());
            Log.d("TAG", "onResume: 5"+mGPS.getstate());

           TextView txt_full_add = inflatedView.findViewById(R.id.txt_full_add);
           TextView txt_add = inflatedView.findViewById(R.id.txt_add);
           TextView txt_lat_log = inflatedView.findViewById(R.id.txt_lat_log);
           TextView txt_temp = inflatedView.findViewById(R.id.txt_temp);
           TextView txt_humidity = inflatedView.findViewById(R.id.txt_humidity);
           TextView txt_time = inflatedView.findViewById(R.id.txt_time);
           TextView txt_add_1 = inflatedView.findViewById(R.id.txt_add_1);
           TextView txt_coma = inflatedView.findViewById(R.id.txt_coma);
           TextView txt_date = inflatedView.findViewById(R.id.txt_date);



           txt_add.setText(mGPS.getCity());
           txt_add_1.setText(mGPS.getcountry());
           txt_full_add.setText(mGPS.getAddressLine());
            txt_lat_log.setText("let : "+new DecimalFormat("##.##").format(mGPS.getLatitude())+" log : "+new DecimalFormat("##.##").format(mGPS.getLongitude()));

            int map = preferences1.getInt("map",1);
            int address = preferences1.getInt("address",1);
            int d_address = preferences1.getInt("d_address",1);
            int coordinate = preferences1.getInt("coordinate",1);
            int temperature = preferences1.getInt("temperature",1);
            int weather = preferences1.getInt("weather",1);
            int time = preferences1.getInt("time",1);

            int timer = preferences.getInt("time",0);

            switch (timer){
                case 0:
                default:
                    off_text.setImageResource(R.drawable.off_press);
                    five_sec_text.setImageResource(R.drawable.five_unpress);
                    ten_sec_text.setImageResource(R.drawable.ten_unpress);
                    break;
                case 1:
                    off_text.setImageResource(R.drawable.off_unpress);
                    five_sec_text.setImageResource(R.drawable.five_press);
                    ten_sec_text.setImageResource(R.drawable.ten_unpress);
                    break;
                case 2:
                    off_text.setImageResource(R.drawable.off_unpress);
                    five_sec_text.setImageResource(R.drawable.five_unpress);
                    ten_sec_text.setImageResource(R.drawable.ten_press);
                    break;

            }




            if(map == 1){
                if(position == 45){

                    relative_fragment_2.setVisibility(View.VISIBLE);
                    widghtsHolders.setVisibility(View.INVISIBLE);

                }else{

                    widghtsHolders.setVisibility(View.VISIBLE);
                }
            }else{
                if(position == 45){
                    relative_fragment_2.setVisibility(View.INVISIBLE);

                }
                widghtsHolders.setVisibility(View.INVISIBLE);
            }

            if(txt_add.getVisibility() != View.GONE) {
                txt_add.setVisibility(address == 1 ? View.VISIBLE : View.INVISIBLE);
                if(txt_add_1.getVisibility() != View.GONE) {
                    txt_add_1.setVisibility(address == 1 ? View.VISIBLE : View.INVISIBLE);
                } if(txt_coma.getVisibility() != View.GONE){
                txt_coma.setVisibility(address == 1 ? View.VISIBLE : View.INVISIBLE);
            }
            }
            if(txt_full_add.getVisibility() != View.GONE) {
                txt_full_add.setVisibility(d_address == 1 ? View.VISIBLE : View.INVISIBLE);
            }
            if(txt_lat_log.getVisibility() != View.GONE) {
                txt_lat_log.setVisibility(coordinate == 1 ? View.VISIBLE : View.INVISIBLE);
            }
            if(txt_temp.getVisibility() != View.GONE) {
                txt_temp.setVisibility(temperature == 1 ? View.VISIBLE : View.INVISIBLE);
            }
            if(txt_humidity.getVisibility() != View.GONE) {
                txt_humidity.setVisibility(weather == 1 ? View.VISIBLE : View.INVISIBLE);
            }
            if(txt_time.getVisibility() != View.GONE) {
                txt_time.setVisibility(time == 1 ? View.VISIBLE : View.INVISIBLE);
                if (txt_date.getVisibility() != View.GONE) {
                    txt_date.setVisibility(time == 1 ? View.VISIBLE : View.INVISIBLE);
                }
            }
            CallWeather();
        } else {
            ActivityCompat.requestPermissions(GpsPhotoActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.ACCESS_COARSE_LOCATION"}, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);

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
                if(txt_temp.getVisibility() != View.GONE) {
                    txt_temp.setText("" + new DecimalFormat("##").format(currentWeather.getMain().getTempMax()) + "°F");
                }
                Log.d("TAG", "onSuccess: 1 "+ new DecimalFormat("##").format(currentWeather.getMain().getTempMax()) + "°F");
                Log.d("TAG", "onSuccess: 2 "+currentWeather.getMain().getHumidity());
                if(txt_temp.getVisibility() != View.GONE) {
                    txt_humidity.setText("" + currentWeather.getMain().getHumidity());
                }
            }
        });
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eid = event.getAction();


        switch (eid) {
            case MotionEvent.ACTION_MOVE:

                PointF mv = new PointF(event.getX() - DownPT.x, event.getY()
                        - DownPT.y);
                 imgWidth = v.getWidth()/2;
                 imgHeight = v.getHeight()/2;
                Log.d("TAG", "startMoving: "+width);
                Log.d("TAG", "startMoving: "+height);

                if(StartPT.x + mv.x + imgWidth > 250 && StartPT.x + mv.x + imgWidth < width-250){
                    v.setX((int) (StartPT.x + mv.x));
                }
                if(StartPT.y + mv.y + imgHeight > 100 && StartPT.y + mv.y + imgHeight < height-100){
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


    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationManager.removeUpdates(this);

        //open the map:
        latitude_map = location.getLatitude();
        longitude_map = location.getLongitude();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    public boolean connectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 29) {
                try {
                    return connectivityManager.getActiveNetworkInfo() != null;
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            } else return connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork()) != null;
        }
        return false;
    }

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}