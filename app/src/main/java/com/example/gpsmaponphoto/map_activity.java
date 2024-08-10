package com.example.gpsmaponphoto;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class map_activity extends AppCompatActivity implements LocationListener, OnMapReadyCallback{

    GoogleMap gmap;
    LatLng latLng;
    SupportMapFragment supportMapFragment;
    TextView address_map,city_map,state_map,country_map;
    EditText latitude,longitude;
    Location location;
    Geocoder geocoder;
    Double latitude_map;
    Editable latitue_edit,longitude_edit;
    Marker mCurrLocation;
    LinearLayout ll_latitude,ll_longitude,ll_city,ll_state,ll_country,ll_location;
    ConstraintLayout googal_map,cl_all_layout;
    Double longitude_map;
    List<Address> addresses;
    LocationManager locationManager;
    ImageView setting_Back_btn,img_minus,img_plus,img_find_loaction,setting_apply_btn;
    private ConstraintLayout constraint_Layout;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        address_map = findViewById(R.id.address_map);
        city_map = findViewById(R.id.city_map);
        state_map = findViewById(R.id.state_map);
        country_map = findViewById(R.id.country_map);
        setting_Back_btn = findViewById(R.id.setting_Back_btn);
        img_minus = findViewById(R.id.img_minus);
        img_plus = findViewById(R.id.img_plus);
        img_find_loaction = findViewById(R.id.img_find_loaction);
        setting_apply_btn = findViewById(R.id.setting_apply_btn);
        constraint_Layout = findViewById(R.id.constraint_Layout);


         ll_latitude = findViewById(R.id.ll_latitude);
         ll_longitude = findViewById(R.id.ll_longitude);
         ll_location = findViewById(R.id.ll_location);
         googal_map = findViewById(R.id.googal_map);
         cl_all_layout = findViewById(R.id.cl_all_layout);
         ll_city = findViewById(R.id.ll_city);
         ll_state = findViewById(R.id.ll_state);
         ll_country = findViewById(R.id.ll_country);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (permission()) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location == null){
                    location = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);

                }
            }


        } else {
            ActivityCompat.requestPermissions(map_activity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.INTERNET","android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.ACCESS_COARSE_LOCATION"}, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        }

        geocoder = new Geocoder(this, Locale.getDefault());

        if(location != null) {
            latitude_map = location.getLatitude();
            longitude_map = location.getLongitude();

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            latLng = new LatLng(location.getLatitude(), location.getLongitude());

            latitude.setText(latitude_map + "");
            longitude.setText(longitude_map + "");



        }

        if(addresses != null) {

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();


            address_map.setText(address);
            city_map.setText(city);
            state_map.setText(state);
            country_map.setText(country);

        }


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);


        setting_Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        img_minus.setOnClickListener(view -> {
            if (gmap != null) {
                gmap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        img_plus.setOnClickListener(view -> {
            if (gmap != null) {
                gmap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        img_find_loaction.setOnClickListener(view -> {
            if(location !=null) {
                latitude.setText(latitude_map + "");
                longitude.setText(longitude_map + "");
            }
            if(gmap != null){
            onMapReady(gmap);
        }
        });

        setting_apply_btn.setOnClickListener(view -> {

            if(gmap !=null) {

                int height = HelperResizer.setHeight(87);
                int width = HelperResizer.setWidth(57);
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.locationpin);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                gmap.clear();
                gmap.getUiSettings().setCompassEnabled(false);
                longitude_edit = longitude.getText();
              if(location != null) {
                  Log.d("TAG", "onClick: " + latitue_edit);
                  Log.d("TAG", "onClick: " + longitude_edit);

                  double latitue_edit = Double.parseDouble(latitude.getText().toString());
                  double longitude_edit = Double.parseDouble(longitude.getText().toString());

                  LatLng sydney = new LatLng(latitue_edit, longitude_edit);
                  gmap.addMarker(new MarkerOptions()
                          .position(sydney).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));


                  gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                  Toast.makeText(this, "location  set successfully", Toast.LENGTH_SHORT).show();
              }
            }
        });
        size();

    }

    private void size() {

        HelperResizer.getheightandwidth(this);
        HelperResizer.setSize(ll_latitude,482,144);
        HelperResizer.setSize(ll_longitude,482,144);

        HelperResizer.setSize(setting_Back_btn,60,65);
        HelperResizer.setPadding(setting_Back_btn,10,10,10,10);
        HelperResizer.setMargin(setting_Back_btn,70,55,0,0);

        HelperResizer.setSize(setting_apply_btn,203,102);
        HelperResizer.setMargin(setting_apply_btn,0,27,60,0);

        HelperResizer.setSize(googal_map,1080,1123);
        HelperResizer.setMargin(googal_map,0,0,0,-75);

        HelperResizer.setSize(img_find_loaction,95,95);
        HelperResizer.setMargin(img_find_loaction,0,250,50,0);

       HelperResizer.setSize(constraint_Layout,1080,231);

        HelperResizer.setSize(img_minus,74,74);
        HelperResizer.setMargin(img_minus,0,0,50,130);

        HelperResizer.setSize(img_plus,74,74);
        HelperResizer.setMargin(img_plus,0,0,10,0);

        HelperResizer.setSize(ll_city,482,144);

        HelperResizer.setSize(ll_state,482,144);

        HelperResizer.setSize(ll_country,482,144);
        HelperResizer.setMargin(ll_country,60,25,0,0);

        HelperResizer.setSize(cl_all_layout,1080,872);

        HelperResizer.setWidth(this,ll_location,950);





    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        int height = HelperResizer.setHeight(89);
        int width = HelperResizer.setWidth(58);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.locationpin);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            gmap = googleMap;
            gmap.clear();
        gmap.getUiSettings().setCompassEnabled(false);

        if(latitude_map != null){
            LatLng sydney = new LatLng(latitude_map, longitude_map);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(sydney);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            gmap.addMarker(markerOptions);
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16.0f));
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (mCurrLocation != null) {
            mCurrLocation.remove();
        }
        int height = HelperResizer.setHeight(87);
        int width = HelperResizer.setWidth(57);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.locationpin);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("you are here");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        mCurrLocation = gmap.addMarker(markerOptions);

    }





public boolean permission() {
    return (((ContextCompat.checkSelfPermission(map_activity.this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(map_activity.this, "android.permission.CAMERA")) + ContextCompat.checkSelfPermission(map_activity.this, "android.permission.RECORD_AUDIO")) + ContextCompat.checkSelfPermission(map_activity.this, "android.permission.ACCESS_FINE_LOCATION")) + ContextCompat.checkSelfPermission(map_activity.this, "android.permission.ACCESS_COARSE_LOCATION") + ContextCompat.checkSelfPermission(map_activity.this, "android.permission.INTERNET") == PackageManager.PERMISSION_GRANTED;
}
}