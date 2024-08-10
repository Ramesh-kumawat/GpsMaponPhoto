package com.example.gpsmaponphoto.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.R;

public class Tab2Fragment extends Fragment {
     ConstraintLayout cl_address,cl_map,cl_d_address,cl_temperature,cl_weather,cl_coordinate,cl_time;
     ImageView check_map,check_address,check_D_Address,check_coordinate,check_temprature,check_weather,check_time;
     TextView map_text,text_address,text_D_Address,text_coordinate,text_temperature,text_weather,text_Time;




    public Tab2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2_fragmet , container , false);


    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Initialize(view);
        size();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("map_type", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor   = sharedPreferences.edit();

        int map = sharedPreferences.getInt("map",1);
        int address = sharedPreferences.getInt("address",1);
        int d_address = sharedPreferences.getInt("d_address",1);
        int coordinate = sharedPreferences.getInt("coordinate",1);
        int temperature = sharedPreferences.getInt("temperature",1);
        int weather = sharedPreferences.getInt("weather",1);
        int time = sharedPreferences.getInt("time",1);


        cl_map.setBackgroundResource(map == 1   ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_address.setBackgroundResource(address == 1  ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_d_address.setBackgroundResource(d_address == 1 ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_coordinate.setBackgroundResource(coordinate == 1  ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_temperature.setBackgroundResource(temperature == 1 ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_weather.setBackgroundResource(weather == 1  ? R.drawable.checkbox : R.drawable.uncheckbox);
        cl_time.setBackgroundResource(time == 1  ? R.drawable.checkbox : R.drawable.uncheckbox);

        map_text.setTextColor(map == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_address.setTextColor(address == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_D_Address.setTextColor(d_address == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_coordinate.setTextColor(coordinate == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_temperature.setTextColor(temperature == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_weather.setTextColor(weather == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
        text_Time.setTextColor(time == 1 ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));

        check_map.setImageResource(map == 1 ? R.drawable.check : R.drawable.uncheck);
        check_address.setImageResource(address == 1 ? R.drawable.check : R.drawable.uncheck);
        check_D_Address.setImageResource(d_address == 1 ? R.drawable.check : R.drawable.uncheck);
        check_coordinate.setImageResource(coordinate == 1 ? R.drawable.check : R.drawable.uncheck);
        check_temprature.setImageResource(temperature == 1 ? R.drawable.check : R.drawable.uncheck);
        check_weather.setImageResource(weather == 1 ? R.drawable.check : R.drawable.uncheck);
        check_time.setImageResource(time == 1 ? R.drawable.check : R.drawable.uncheck);



        cl_map.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {
                int map = sharedPreferences.getInt("map",1);

                if(map == 1){
                    i = 1;
                    Log.d("TAG", "onClick: 1");
                }else{
                    Log.d("TAG", "onClick: 0");
                    i = 0;
                }

                if(i == 0){
                    Log.d("TAG", "onClick: map 1");
                    check_map.setImageResource(R.drawable.check);
                    cl_map.setBackgroundResource(R.drawable.checkbox);
                    map_text.setTextColor(getResources().getColor(R.color.black));

                    editor.putInt("map",1);
                    editor.apply();
                    i = 1;
                }else{
                    Log.d("TAG", "onClick: map 0");
                    check_map.setImageResource(R.drawable.uncheck);
                    cl_map.setBackgroundResource(R.drawable.uncheckbox);
                    map_text.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("map",0);
                    editor.apply();
                    i = 0;
                }
            }
        });

        cl_address.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View view) {

                 int address = sharedPreferences.getInt("address",1);

                if(address == 1){
                    i = 1;
                }else{
                    i = 0;
                }

                if(i == 0){
                    Log.d("TAG", "onClick: address 1");

                check_address.setImageResource(R.drawable.check);
                cl_address.setBackgroundResource(R.drawable.checkbox);
                text_address.setTextColor(getResources().getColor(R.color.black));
                    editor.putInt("address",1);
                    editor.apply();
                    i = 1;
                }else{
                    Log.d("TAG", "onClick: address 0");
                     check_address.setImageResource(R.drawable.uncheck);
                    cl_address.setBackgroundResource(R.drawable.uncheckbox);
                    text_address.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("address",0);
                    editor.apply();
                    i = 0;
                }
            }
        });

        cl_d_address.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {
                int d_address = sharedPreferences.getInt("d_address",1);

                if(d_address == 1){
                    i = 1;
                }else{
                    i = 0;
                }

                if(i == 0){
                    Log.d("TAG", "onClick: check 1");
                    check_D_Address.setImageResource(R.drawable.check);
                    cl_d_address.setBackgroundResource(R.drawable.checkbox);
                    text_D_Address.setTextColor(getResources().getColor(R.color.black));


                    editor.putInt("d_address",1);
                    editor.apply();
                    i = 1;
                }else{
                    Log.d("TAG", "onClick: check 0");

                    check_D_Address.setImageResource(R.drawable.uncheck);
                    cl_d_address.setBackgroundResource(R.drawable.uncheckbox);
                    text_D_Address.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("d_address",0);
                    editor.apply();
                    i = 0;
                }
            }
        });

        cl_coordinate.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {

                int coordinate = sharedPreferences.getInt("coordinate",1);

                if(coordinate == 1){
                    i = 1;
                }else{
                    i = 0;
                }

                if(i == 0){

                    check_coordinate.setImageResource(R.drawable.check);
                    cl_coordinate.setBackgroundResource(R.drawable.checkbox);
                    text_coordinate.setTextColor(getResources().getColor(R.color.black));


                    editor.putInt("coordinate",1);
                    editor.apply();
                    i = 1;
                }else{

                    check_coordinate.setImageResource(R.drawable.uncheck);
                    cl_coordinate.setBackgroundResource(R.drawable.uncheckbox);
                   text_coordinate.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("coordinate",0);
                    editor.apply();
                    i = 0;
                }

            }
        });

        cl_temperature.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {
                int temperature = sharedPreferences.getInt("temperature",1);

                if(temperature == 1){
                    i = 1;
                }else{
                    i = 0;
                }

                if(i == 0){
                    check_temprature.setImageResource(R.drawable.check);
                    cl_temperature.setBackgroundResource(R.drawable.checkbox);
                    text_temperature.setTextColor(getResources().getColor(R.color.black));

                    editor.putInt("temperature",1);
                    editor.apply();
                    i = 1;
                }else{

                    check_temprature.setImageResource(R.drawable.uncheck);
                    cl_temperature.setBackgroundResource(R.drawable.uncheckbox);
                    text_temperature.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("temperature",0);
                    editor.apply();
                    i = 0;
                }

            }
        });
        cl_weather.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {

                int weather = sharedPreferences.getInt("weather",1);

                if(weather == 1){
                    i = 1;
                }else{
                    i = 0;
                }
                if(i == 0){

                    check_weather.setImageResource(R.drawable.check);
                    cl_weather.setBackgroundResource(R.drawable.checkbox);
                    text_weather.setTextColor(getResources().getColor(R.color.black));

                    editor.putInt("weather",1);
                    editor.apply();
                    i = 1;
                }else{

                    check_weather.setImageResource(R.drawable.uncheck);
                    cl_weather.setBackgroundResource(R.drawable.uncheckbox);
                    text_weather.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("weather",0);
                    editor.apply();
                    i = 0;
                }

            }
        });
           cl_time.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View view) {

                int time = sharedPreferences.getInt("time",1);

                if(time == 1){
                    i = 1;
                }else{
                    i = 0;
                }
                if(i == 0){

                    check_time.setImageResource(R.drawable.check);
                    cl_time.setBackgroundResource(R.drawable.checkbox);
                    text_Time.setTextColor(getResources().getColor(R.color.black));

                    editor.putInt("time",1);
                    editor.apply();
                    i = 1;
                }else{

                    check_time.setImageResource(R.drawable.uncheck);
                    cl_time.setBackgroundResource(R.drawable.uncheckbox);
                    text_Time.setTextColor(getResources().getColor(R.color.white));

                    editor.putInt("time",0);
                    editor.apply();
                    i = 0;
                }

            }
        });

    }

    private void size() {

        HelperResizer.getheightandwidth(getContext());

        HelperResizer.setSize(cl_map,980,163);
        HelperResizer.setMargin(cl_map,0,35,0,0);

        HelperResizer.setSize(cl_address,980,163);
        HelperResizer.setMargin(cl_address,0,35,0,0);

        HelperResizer.setSize(cl_d_address,980,163);
        HelperResizer.setMargin(cl_d_address,0,35,0,0);

        HelperResizer.setSize(cl_coordinate,980,163);
        HelperResizer.setMargin(cl_coordinate,0,35,0,0);

        HelperResizer.setSize(cl_temperature,980,163);
        HelperResizer.setMargin(cl_temperature,0,35,0,0);

        HelperResizer.setSize(cl_weather,980,163);
        HelperResizer.setMargin(cl_weather,0,35,0,0);

        HelperResizer.setSize(cl_time,980,163);
        HelperResizer.setMargin(cl_time,0,35,0,0);


        HelperResizer.setSize(check_map,55,55);
        HelperResizer.setMargin(check_map,85,0,0,0);

        HelperResizer.setSize(check_address,55,55);
        HelperResizer.setMargin(check_address,85,0,0,0);

        HelperResizer.setSize(check_D_Address,55,55);
        HelperResizer.setMargin(check_D_Address,85,0,0,0);

        HelperResizer.setSize(check_coordinate,55,55);
        HelperResizer.setMargin(check_coordinate,85,0,0,0);

        HelperResizer.setSize(check_temprature,55,55);
        HelperResizer.setMargin(check_temprature,85,0,0,0);

        HelperResizer.setSize(check_weather,55,55);
        HelperResizer.setMargin(check_weather,85,0,0,0);

        HelperResizer.setSize(check_time,55,55);
        HelperResizer.setMargin(check_time,85,0,0,0);


    }


    private void Initialize(View view) {

        cl_map = view.findViewById(R.id.cl_map);
        cl_address = view.findViewById(R.id.cl_address);
        cl_d_address = view.findViewById(R.id.cl_d_address);
        cl_coordinate = view.findViewById(R.id.cl_coordinate);
        cl_temperature = view.findViewById(R.id.cl_temperature);
        cl_weather = view.findViewById(R.id.cl_weather);
        cl_time = view.findViewById(R.id.cl_time);

        check_map = view.findViewById(R.id.check_map);
        check_address = view.findViewById(R.id.check_address);
        check_D_Address = view.findViewById(R.id.check_D_Address);
        check_coordinate = view.findViewById(R.id.check_coordinate);
        check_temprature = view.findViewById(R.id.check_temperature);
        check_weather = view.findViewById(R.id.check_weather);
        check_time = view.findViewById(R.id.check_Time);

        map_text = view.findViewById(R.id.map_text);
        text_address = view.findViewById(R.id.text_address);
        text_D_Address = view.findViewById(R.id.text_D_Address);
        text_coordinate = view.findViewById(R.id.text_coordinate);
        text_temperature = view.findViewById(R.id.text_temperature);
        text_weather = view.findViewById(R.id.text_weather);
        text_Time = view.findViewById(R.id.text_None);
    }


}
