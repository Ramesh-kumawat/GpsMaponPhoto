package com.example.gpsmaponphoto.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.Models.pictureFacer;
import com.example.gpsmaponphoto.R;

import java.util.ArrayList;

public class picture_Adapter extends RecyclerView.Adapter<picture_Adapter.PicHolder>{

     ArrayList<pictureFacer> pictureList;
     Context pictureContx;
     onitemClick picListerner;


    public picture_Adapter(ArrayList<pictureFacer> pictureList, Context pictureContx, onitemClick picListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.picListerner = picListerner;
    }

    @NonNull
    @Override
    public picture_Adapter.PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.gallery_layout, parent, false);
        return new PicHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull picture_Adapter.PicHolder holder, int position) {

         int i = position;
         pictureFacer image = pictureList.get(position);
        Log.d("TAG", "onBindViewHolder: "+pictureList.size());


        HelperResizer.setSize(holder.card_view,348,360);
            Glide.with(pictureContx)
                    .load(image.getPicturePath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(holder.gallery_img_view_img);

        holder.gallery_img_view_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picListerner.onPicClicked(i, image.getPicturePath(),image.getPicturName());

            }
        });






    }
    public interface onitemClick {
//                void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics);
        void onPicClicked(int pos, String pictureFolderPath,String folderName);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public static class PicHolder extends RecyclerView.ViewHolder {

        ImageView gallery_img_view_img;
        CardView card_view;

        public PicHolder(@NonNull View itemView) {
            super(itemView);
            gallery_img_view_img = itemView.findViewById(R.id.gallery_img_view_img);
            card_view = itemView.findViewById(R.id.card_view);

        }
    }
}
