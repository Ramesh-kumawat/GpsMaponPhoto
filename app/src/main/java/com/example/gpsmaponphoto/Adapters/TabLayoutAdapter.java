package com.example.gpsmaponphoto.Adapters;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gpsmaponphoto.Fragments.Tab1Fragmet;
import com.example.gpsmaponphoto.Fragments.Tab2Fragment;
import com.example.gpsmaponphoto.Fragments.Tab3Fragment;
import com.example.gpsmaponphoto.Fragments.Tab4Fragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;

    public TabLayoutAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
            default:
                return new Tab1Fragmet();
            case 1:
                return new Tab2Fragment();
            case 2:
                return new Tab3Fragment();
            case 3:
                return new Tab4Fragment();

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}