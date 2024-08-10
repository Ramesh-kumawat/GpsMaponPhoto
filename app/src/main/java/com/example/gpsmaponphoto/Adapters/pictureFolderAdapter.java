package com.example.gpsmaponphoto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.gpsmaponphoto.HelperResizer;
import com.example.gpsmaponphoto.Models.imageFolder;
import com.example.gpsmaponphoto.R;

import java.util.ArrayList;

public class pictureFolderAdapter extends RecyclerView.Adapter<pictureFolderAdapter.FolderHolder>{

      ArrayList<imageFolder> folders;
      Context folderContx;
      itemClickListener listenToClick;

    public pictureFolderAdapter(ArrayList<imageFolder> folders, Context folderContx, itemClickListener listenToClick) {
        this.folders = folders;
        this.folderContx = folderContx;
        this.listenToClick = listenToClick;
    }

    @NonNull
    @Override
    public pictureFolderAdapter.FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.picture_folder_item, parent, false);
        return new FolderHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull pictureFolderAdapter.FolderHolder holder, int position) {

        final imageFolder folder = folders.get(position);

        Glide.with(folderContx).load(folder.getFirstPic()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).apply(new RequestOptions().centerCrop()).into(holder.folderPic);



        holder.folderName.setText(folder.getFolderName());


        HelperResizer.setSize(holder.folderCard,892,312);

        holder.folderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenToClick.onPicClicked(folder.getPath(),folder.getFolderName());
            }
        });



    }

    public interface itemClickListener {
//        void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics);
        void onPicClicked(String pictureFolderPath,String folderName);
    }
    @Override
    public int getItemCount() {
        return folders.size();
    }

    public static class FolderHolder extends RecyclerView.ViewHolder {

        ImageView folderPic;
        TextView folderName;
        CardView folderCard;

        public FolderHolder(@NonNull View itemView) {
            super(itemView);

            folderPic = itemView.findViewById(R.id.folder_image_view);
            folderName = itemView.findViewById(R.id.folder_text_view);
            folderCard = itemView.findViewById(R.id.folder_card_view);


        }


    }

}
