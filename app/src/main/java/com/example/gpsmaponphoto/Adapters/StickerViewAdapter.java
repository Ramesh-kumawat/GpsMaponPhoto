package com.example.gpsmaponphoto.Adapters;

import static com.example.gpsmaponphoto.Fragments.Tab1Fragmet.tabselector;
import static com.example.gpsmaponphoto.GalleryViewActivity.selected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.R;
import com.example.gpsmaponphoto.Models.StickerModel;

import java.util.ArrayList;

public class StickerViewAdapter extends  RecyclerView.Adapter<StickerViewAdapter.ViewHolder> {

    ArrayList<StickerModel> modelArrayList;
    Context context;
    onpositionselect onpositionselect;
    int id;





    public StickerViewAdapter(ArrayList<StickerModel> modelArrayList, Context context,onpositionselect onpositionselect,int id) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.onpositionselect = onpositionselect;
        this.id = id;


    }


    @NonNull
    @Override
    public StickerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate;
        if(id != 1) {
            inflate = LayoutInflater.from(context).inflate(R.layout.sticker_recycle_view, parent, false);
        }else {
            inflate = LayoutInflater.from(context).inflate(R.layout.image_sticker_recycle_view, parent, false);
        }
        return new ViewHolder(inflate);
    }



    @Override
    public void onBindViewHolder(@NonNull StickerViewAdapter.ViewHolder holder, int position) {

        int i = position;


         if(id == 1) {
             holder.stickerholder_2.setImageResource(modelArrayList.get(position).getImglayout());


             if (selected == position) {

                 holder.imageView3.setImageResource(R.drawable.check);


             } else {
                 holder.imageView3.setImageResource(R.drawable.uncheck);
             }


             holder.stickerholder_2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     onpositionselect.postionOfItem(i);


                 }
             });
         }else{

             holder.stickerholder.setImageResource(modelArrayList.get(position).getLayout());


             if(tabselector == position){
                 holder.cbSelect.setVisibility(View.VISIBLE);

             } else {
                 holder.cbSelect.setVisibility(View.INVISIBLE);
             }

             holder.cl_click_check.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     onpositionselect.postionOfItem(i);


                 }
             });

         }
    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



            ImageView cbSelect;
            ImageView stickerholder;
            ConstraintLayout cl_click_check;

            ImageView stickerholder_2;
            ImageView imageView3;
            ImageView overlay;







        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            stickers  = itemView.findViewById(R.id.stickers);


            if(id != 1) {
                cbSelect = itemView.findViewById(R.id.cbSelect);
                stickerholder = itemView.findViewById(R.id.stickerholder);
                cl_click_check = itemView.findViewById(R.id.cl_click_check);
                HelperResizer.setSize(cbSelect, 980, 312);
                HelperResizer.setSize(stickerholder, 980, 312);
            }else{
                stickerholder_2 = itemView.findViewById(R.id.stickerholder_2);
                imageView3 = itemView.findViewById(R.id.imageView3);
                overlay = itemView.findViewById(R.id.overlay);

                HelperResizer.setSize(stickerholder_2,723,268);
                HelperResizer.setSize(overlay, 841, 268);
                HelperResizer.setSize(imageView3, 55, 55);
                HelperResizer.setMargin(imageView3, 40,0,0, 0);
            }

        }

    }

    public  interface onpositionselect {

         void postionOfItem(int position);
    }

}
