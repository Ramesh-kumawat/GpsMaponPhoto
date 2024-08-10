package com.example.gpsmaponphoto.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.R;

public class Tab4Fragment extends Fragment {

    ImageView edit_btn,widget_edit;
    SharedPreferences sharedPreferences;
    public Tab4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab4_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_btn = view.findViewById(R.id.edit_btn);
        widget_edit = view.findViewById(R.id.widget_edit);


        HelperResizer.getheightandwidth(getContext());
        HelperResizer.setSize(edit_btn,360,146);
        HelperResizer.setMargin(edit_btn,0,0,0,55);
        HelperResizer.setSize(widget_edit,577,575);
        HelperResizer.setMargin(widget_edit,0,445,0,0);


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 sharedPreferences = getActivity().getSharedPreferences("map_type", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = sharedPreferences.edit();
                 editor.putInt("edit",1);
                 editor.apply();
                Toast.makeText(getContext(), "Editing Enable", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

    }

    }
