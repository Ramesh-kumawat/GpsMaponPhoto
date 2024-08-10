package com.example.gpsmaponphoto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gpsmaponphoto.R;

import java.util.ArrayList;


public class AdapterCollection extends RecyclerView.Adapter<AdapterCollection.ViewHolder> {

    Context context;
    ArrayList<String> collectionmodels;

    public AdapterCollection(Context context, ArrayList<String> collectionmodels) {
        this.context = context;
        this.collectionmodels = collectionmodels;
    }

    @NonNull
    @Override
    public AdapterCollection.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.gallery_layout, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCollection.ViewHolder holder, int position) {

        String i = collectionmodels.get(position);
        Glide.with(context).load(i).centerCrop().placeholder(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.gallery_img_view_img);


    }

    @Override
    public int getItemCount() {
        return collectionmodels.size();
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
