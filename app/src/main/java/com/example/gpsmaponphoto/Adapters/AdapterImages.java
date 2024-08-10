package com.example.gpsmaponphoto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gpsmaponphoto.GalleryViewActivity;
import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.R;

import java.util.ArrayList;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolder> {
    Context context;
    ArrayList<String> folderModels;



    public AdapterImages(Context context, ArrayList<String> folderModels) {
        this.context = context;
        this.folderModels = folderModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.gallery_layout, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {

        String i = folderModels.get(position);
        Glide.with(context).load(i).centerCrop().placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.gallery_img_view_img);

        HelperResizer.setSize(holder.back_button,22,39);
        HelperResizer.setMargin(holder.back_button,70,0,0,0);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");
                Intent intent = new Intent(context, GalleryViewActivity.class);
                intent.putExtra("ImagePath",i);
                context.startActivity(intent);
              }
        });
    }
    public long getItemId(int i) {
        return  i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return folderModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView back_button;
        ImageView gallery_img_view_img;
         RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycle_images_gallery);
            back_button = itemView.findViewById(R.id.img_back_gallery);
            gallery_img_view_img = itemView.findViewById(R.id.gallery_img_view_img);
        }
    }
}
