package com.example.gpsmaponphoto.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;

import com.example.gpsmaponphoto.Models.StickerModel;
import com.example.gpsmaponphoto.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

 public class AllLocation implements LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 1;
    List<Address> addresses;
    boolean canGetLocation = false;
    Geocoder geocoder;
    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    LocationManager locationManager;
    double longitude;
    public Activity mContext;


    public void onLocationChanged(Location location2) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public AllLocation(Activity activity) {
        this.mContext = activity;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation()  {

        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("isGPSEnabled", "=" + isGPSEnabled);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);
            geocoder = new Geocoder(mContext, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (!isGPSEnabled || !isNetworkEnabled) {
                Toast.makeText(mContext, "Please enable Mobile Data / wifi", Toast.LENGTH_SHORT).show();

            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    location=null;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) { location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                geocoder = new Geocoder(mContext, Locale.getDefault());

                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (isGPSEnabled) {

                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;

    }


    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
           longitude = location.getLongitude();
        }
        return longitude;
    }

    public  String getAddressLine() {
        if(addresses != null){
            if(addresses.size() > 0){
             return addresses.get(0).getAddressLine(0);

            }else{
                return "address";
            }
        }

        return null;
    }

    public  String getCity() {
        if(addresses != null){
            if(addresses.size() > 0){
                return addresses.get(0).getLocality();

            }else{
                return "city";
            }
        }

        return null;
    }

    public String getstate() {

        if(addresses != null){
            if(addresses.size() > 0){
                return addresses.get(0).getAdminArea();

            }else{
                return "state";
            }
        }

        return null;

    }

    public String getcountry() {
        if(addresses != null){
            if(addresses.size() > 0){
                return addresses.get(0).getCountryName();

            }else{
                return "country";
            }
        }
        return null;

    }


    public void AddNewView(ArrayList<StickerModel> arrayList){


        arrayList.add(new StickerModel(R.drawable.template1,R.drawable.templateimg1));
        arrayList.add(new StickerModel(R.drawable.template2,R.drawable.templateimg2));
        arrayList.add(new StickerModel(R.drawable.template3,R.drawable.templateimg3));
        arrayList.add(new StickerModel(R.drawable.template4,R.drawable.templateimg4));
        arrayList.add(new StickerModel(R.drawable.template5,R.drawable.templateimg5));
        arrayList.add(new StickerModel(R.drawable.template6,R.drawable.templateimg6));
        arrayList.add(new StickerModel(R.drawable.template7,R.drawable.templateimg7));
        arrayList.add(new StickerModel(R.drawable.template8,R.drawable.templateimg8));
        arrayList.add(new StickerModel(R.drawable.template9,R.drawable.templateimg9));
        arrayList.add(new StickerModel(R.drawable.template10,R.drawable.templateimg10));
        arrayList.add(new StickerModel(R.drawable.template11,R.drawable.templateimg11));
        arrayList.add(new StickerModel(R.drawable.template12,R.drawable.templateimg12));
        arrayList.add(new StickerModel(R.drawable.template13,R.drawable.templateimg13));
        arrayList.add(new StickerModel(R.drawable.template14,R.drawable.templateimg14));
        arrayList.add(new StickerModel(R.drawable.template15,R.drawable.templateimg15));
        arrayList.add(new StickerModel(R.drawable.template16,R.drawable.templateimg16));
        arrayList.add(new StickerModel(R.drawable.template17,R.drawable.templateimg17));
        arrayList.add(new StickerModel(R.drawable.template18,R.drawable.templateimg18));
        arrayList.add(new StickerModel(R.drawable.template19,R.drawable.templateimg19));
        arrayList.add(new StickerModel(R.drawable.template20,R.drawable.templateimg20));



    }
}

