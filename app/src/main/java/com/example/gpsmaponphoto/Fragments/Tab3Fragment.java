package com.example.gpsmaponphoto.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Tab3Fragment extends Fragment {

    ConstraintLayout cl_none,cl_Normal,cl_Hybrid,cl_Satellite,cl_Terrain;
    ImageView check_None,check_Normal,check_Hybrid,check_Satellite,check_Terrain;
     TextView text_None,text_Normal,text_Satellite,text_Hybrid,text_Terrain;

    public Tab3Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab3_fragmet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Initialize(view);
        size();



        SharedPreferences sharedPreferences = getContext().getSharedPreferences("map_type", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor   = sharedPreferences.edit();
        int i =  sharedPreferences.getInt("type",1);

        switch (i){
            case 0:
                check_None.setImageResource(R.drawable.check);
                check_Normal.setImageResource(R.drawable.uncheck);
                check_Hybrid.setImageResource(R.drawable.uncheck);
                check_Satellite.setImageResource(R.drawable.uncheck);
                check_Terrain.setImageResource(R.drawable.uncheck);

                cl_none.setBackgroundResource(R.drawable.checkbox);
                cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
                cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
                cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
                cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

                text_None.setTextColor(getResources().getColor(R.color.black));
                text_Normal.setTextColor(getResources().getColor(R.color.white));
                text_Hybrid.setTextColor(getResources().getColor(R.color.white));
                text_Satellite.setTextColor(getResources().getColor(R.color.white));
                text_Terrain.setTextColor(getResources().getColor(R.color.white));

                break;
            case 1:
                check_Normal.setImageResource(R.drawable.check);
                check_None.setImageResource(R.drawable.uncheck);
                check_Hybrid.setImageResource(R.drawable.uncheck);
                check_Satellite.setImageResource(R.drawable.uncheck);
                check_Terrain.setImageResource(R.drawable.uncheck);


                cl_none.setBackgroundResource(R.drawable.uncheckbox);
                cl_Normal.setBackgroundResource(R.drawable.checkbox);
                cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
                cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
                cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

                text_None.setTextColor(getResources().getColor(R.color.white));
                text_Normal.setTextColor(getResources().getColor(R.color.black));
                text_Hybrid.setTextColor(getResources().getColor(R.color.white));
                text_Satellite.setTextColor(getResources().getColor(R.color.white));
                text_Terrain.setTextColor(getResources().getColor(R.color.white));


                break;
            case 2:
                check_Hybrid.setImageResource(R.drawable.check);
                check_Normal.setImageResource(R.drawable.uncheck);
                check_None.setImageResource(R.drawable.uncheck);
                check_Satellite.setImageResource(R.drawable.uncheck);
                check_Terrain.setImageResource(R.drawable.uncheck);


                cl_none.setBackgroundResource(R.drawable.uncheckbox);
                cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
                cl_Hybrid.setBackgroundResource(R.drawable.checkbox);
                cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
                cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

                text_None.setTextColor(getResources().getColor(R.color.white));
                text_Normal.setTextColor(getResources().getColor(R.color.white));
                text_Hybrid.setTextColor(getResources().getColor(R.color.black));
                text_Satellite.setTextColor(getResources().getColor(R.color.white));
                text_Terrain.setTextColor(getResources().getColor(R.color.white));


                break;
            case 3:
                check_Satellite.setImageResource(R.drawable.check);
                check_Normal.setImageResource(R.drawable.uncheck);
                check_Hybrid.setImageResource(R.drawable.uncheck);
                check_None.setImageResource(R.drawable.uncheck);
                check_Terrain.setImageResource(R.drawable.uncheck);


                cl_none.setBackgroundResource(R.drawable.uncheckbox);
                cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
                cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
                cl_Satellite.setBackgroundResource(R.drawable.checkbox);
                cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

                text_None.setTextColor(getResources().getColor(R.color.white));
                text_Normal.setTextColor(getResources().getColor(R.color.white));
                text_Hybrid.setTextColor(getResources().getColor(R.color.white));
                text_Satellite.setTextColor(getResources().getColor(R.color.black));
                text_Terrain.setTextColor(getResources().getColor(R.color.white));



                break;
            case 4:
                check_Terrain.setImageResource(R.drawable.check);
                check_Normal.setImageResource(R.drawable.uncheck);
                check_Hybrid.setImageResource(R.drawable.uncheck);
                check_Satellite.setImageResource(R.drawable.uncheck);
                check_None.setImageResource(R.drawable.uncheck);


                cl_none.setBackgroundResource(R.drawable.uncheckbox);
                cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
                cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
                cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
                cl_Terrain.setBackgroundResource(R.drawable.checkbox);

                text_None.setTextColor(getResources().getColor(R.color.white));
                text_Normal.setTextColor(getResources().getColor(R.color.white));
                text_Hybrid.setTextColor(getResources().getColor(R.color.white));
                text_Satellite.setTextColor(getResources().getColor(R.color.white));
                text_Terrain.setTextColor(getResources().getColor(R.color.black));

                break;
        }






        cl_none.setOnClickListener(view1 -> {

            check_None.setImageResource(R.drawable.check);
            check_Normal.setImageResource(R.drawable.uncheck);
            check_Hybrid.setImageResource(R.drawable.uncheck);
            check_Satellite.setImageResource(R.drawable.uncheck);
            check_Terrain.setImageResource(R.drawable.uncheck);

            cl_none.setBackgroundResource(R.drawable.checkbox);
            cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
            cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
            cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
            cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

            text_None.setTextColor(getResources().getColor(R.color.black));
            text_Normal.setTextColor(getResources().getColor(R.color.white));
            text_Hybrid.setTextColor(getResources().getColor(R.color.white));
            text_Satellite.setTextColor(getResources().getColor(R.color.white));
            text_Terrain.setTextColor(getResources().getColor(R.color.white));


            editor.putInt("type",0);
            editor.apply();
        });

        cl_Normal.setOnClickListener(view12 -> {
            check_Normal.setImageResource(R.drawable.check);
            check_None.setImageResource(R.drawable.uncheck);
            check_Hybrid.setImageResource(R.drawable.uncheck);
            check_Satellite.setImageResource(R.drawable.uncheck);
            check_Terrain.setImageResource(R.drawable.uncheck);

            cl_none.setBackgroundResource(R.drawable.uncheckbox);
            cl_Normal.setBackgroundResource(R.drawable.checkbox);
            cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
            cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
            cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

            text_None.setTextColor(getResources().getColor(R.color.white));
            text_Normal.setTextColor(getResources().getColor(R.color.black));
            text_Hybrid.setTextColor(getResources().getColor(R.color.white));
            text_Satellite.setTextColor(getResources().getColor(R.color.white));
            text_Terrain.setTextColor(getResources().getColor(R.color.white));


            editor.putInt("type",1);
            editor.apply();
        });
        cl_Hybrid.setOnClickListener(view13 -> {
            check_Hybrid.setImageResource(R.drawable.check);
            check_Normal.setImageResource(R.drawable.uncheck);
            check_None.setImageResource(R.drawable.uncheck);
            check_Satellite.setImageResource(R.drawable.uncheck);
            check_Terrain.setImageResource(R.drawable.uncheck);

            cl_none.setBackgroundResource(R.drawable.uncheckbox);
            cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
            cl_Hybrid.setBackgroundResource(R.drawable.checkbox);
            cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
            cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

            text_None.setTextColor(getResources().getColor(R.color.white));
            text_Normal.setTextColor(getResources().getColor(R.color.white));
            text_Hybrid.setTextColor(getResources().getColor(R.color.black));
            text_Satellite.setTextColor(getResources().getColor(R.color.white));
            text_Terrain.setTextColor(getResources().getColor(R.color.white));

            editor.putInt("type",2);
            editor.apply();
        });

        cl_Satellite.setOnClickListener(view14 -> {
            check_Satellite.setImageResource(R.drawable.check);
            check_Normal.setImageResource(R.drawable.uncheck);
            check_Hybrid.setImageResource(R.drawable.uncheck);
            check_None.setImageResource(R.drawable.uncheck);
            check_Terrain.setImageResource(R.drawable.uncheck);

            cl_none.setBackgroundResource(R.drawable.uncheckbox);
            cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
            cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
            cl_Satellite.setBackgroundResource(R.drawable.checkbox);
            cl_Terrain.setBackgroundResource(R.drawable.uncheckbox);

            text_None.setTextColor(getResources().getColor(R.color.white));
            text_Normal.setTextColor(getResources().getColor(R.color.white));
            text_Hybrid.setTextColor(getResources().getColor(R.color.white));
            text_Satellite.setTextColor(getResources().getColor(R.color.black));
            text_Terrain.setTextColor(getResources().getColor(R.color.white));

            editor.putInt("type",3);
            editor.apply();
        });

        cl_Terrain.setOnClickListener(view15 -> {

            check_Terrain.setImageResource(R.drawable.check);
            check_Normal.setImageResource(R.drawable.uncheck);
            check_Hybrid.setImageResource(R.drawable.uncheck);
            check_Satellite.setImageResource(R.drawable.uncheck);
            check_None.setImageResource(R.drawable.uncheck);

            cl_none.setBackgroundResource(R.drawable.uncheckbox);
            cl_Normal.setBackgroundResource(R.drawable.uncheckbox);
            cl_Hybrid.setBackgroundResource(R.drawable.uncheckbox);
            cl_Satellite.setBackgroundResource(R.drawable.uncheckbox);
            cl_Terrain.setBackgroundResource(R.drawable.checkbox);

            text_None.setTextColor(getResources().getColor(R.color.white));
            text_Normal.setTextColor(getResources().getColor(R.color.white));
            text_Hybrid.setTextColor(getResources().getColor(R.color.white));
            text_Satellite.setTextColor(getResources().getColor(R.color.white));
            text_Terrain.setTextColor(getResources().getColor(R.color.black));

            editor.putInt("type",4);
            editor.apply();
        });
    }

    private void size() {


        HelperResizer.getheightandwidth(getContext());

        HelperResizer.setSize(cl_none,980,163);
        HelperResizer.setMargin(cl_none,0,35,0,0);

        HelperResizer.setSize(cl_Normal,980,163);
        HelperResizer.setMargin(cl_Normal,0,35,0,0);

        HelperResizer.setSize(cl_Hybrid,980,163);
        HelperResizer.setMargin(cl_Hybrid,0,35,0,0);

        HelperResizer.setSize(cl_Satellite,980,163);
        HelperResizer.setMargin(cl_Satellite,0,35,0,0);

        HelperResizer.setSize(cl_Terrain,980,163);
        HelperResizer.setMargin(cl_Terrain,0,35,0,0);

        HelperResizer.setSize(check_None,55,55);
        HelperResizer.setMargin(check_None,85,0,0,0);

        HelperResizer.setSize(check_Normal,55,55);
        HelperResizer.setMargin(check_Normal,85,0,0,0);

        HelperResizer.setSize(check_Hybrid,55,55);
        HelperResizer.setMargin(check_Hybrid,85,0,0,0);

        HelperResizer.setSize(check_Satellite,55,55);
        HelperResizer.setMargin(check_Satellite,85,0,0,0);

        HelperResizer.setSize(check_Terrain,55,55);
        HelperResizer.setMargin(check_Terrain,85,0,0,0);
    }

    private void Initialize(View view) {

        cl_none = view.findViewById(R.id.cl_none);
        cl_Normal = view.findViewById(R.id.cl_Normal);
        cl_Hybrid = view.findViewById(R.id.cl_Hybrid);
        cl_Terrain = view.findViewById(R.id.cl_Terrain);
        cl_Satellite = view.findViewById(R.id.cl_Satellite);

        check_None = view.findViewById(R.id.check_None);
        check_Normal = view.findViewById(R.id.check_Normal);
        check_Hybrid = view.findViewById(R.id.check_Hybrid);
        check_Terrain = view.findViewById(R.id.check_Terrain);
        check_Satellite = view.findViewById(R.id.check_Satellite);

        text_None = view.findViewById(R.id.text_None);
        text_Normal = view.findViewById(R.id.text_Normal);
        text_Hybrid = view.findViewById(R.id.text_Hybrid);
        text_Terrain = view.findViewById(R.id.text_Terrain);
        text_Satellite = view.findViewById(R.id.text_Satellite);



    }
}