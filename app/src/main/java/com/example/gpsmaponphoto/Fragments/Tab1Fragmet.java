package com.example.gpsmaponphoto.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsmaponphoto.R;
import com.example.gpsmaponphoto.Models.StickerModel;
import com.example.gpsmaponphoto.Adapters.StickerViewAdapter;
import com.example.gpsmaponphoto.Utils.AllLocation;

import java.util.ArrayList;

public class Tab1Fragmet extends Fragment
        implements
        StickerViewAdapter.onpositionselect
{

    RecyclerView recyclerView;
    StickerViewAdapter stickerViewAdapter;
    ArrayList<StickerModel> stickerModels = new ArrayList<>();
    AllLocation allLocation;
    public static int tabselector = -1;



    public Tab1Fragmet() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab1_fragmet, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            allLocation = new AllLocation(getActivity());

        stickerModels.clear();
        allLocation.AddNewView(stickerModels);

        recyclerView = view.findViewById(R.id.recycle_templates);
        stickerViewAdapter = new StickerViewAdapter(stickerModels,getContext(),this,2);
        recyclerView.setAdapter(stickerViewAdapter);

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void postionOfItem(int position) {

        tabselector = position;
        stickerViewAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("map_type", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putInt("MainLayout",position);
       editor.apply();
    }
}
