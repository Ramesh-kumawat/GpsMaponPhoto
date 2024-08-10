package com.example.gpsmaponphoto;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsmaponphoto.Adapters.picture_Adapter;
import com.example.gpsmaponphoto.Models.pictureFacer;

import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity implements picture_Adapter.onitemClick {

    RecyclerView recycleImages;
    ImageView img_back_gallery;

    TextView tv_file_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        tv_file_name = findViewById(R.id.tv_file_name);
        recycleImages = findViewById(R.id.recycle_images_gallery);
        img_back_gallery = findViewById(R.id.img_back_gallery);


        HelperResizer.getheightandwidth(this);
        HelperResizer.setSize(img_back_gallery,60,65);
        HelperResizer.setPadding(img_back_gallery,10,10,10,10);
        HelperResizer.setMargin(img_back_gallery,70,0,0,0);

        Intent intent = getIntent();
        String path = intent.getStringExtra("folderPath");
        String name = intent.getStringExtra("folderName");

        ArrayList<pictureFacer> imgs;

        img_back_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        recycleImages.hasFixedSize();
        imgs = getAllImagesByFolder(path);
        recycleImages.setAdapter(new picture_Adapter(imgs,GalleryActivity.this,this));
        recycleImages.setLayoutManager(new GridLayoutManager(this,3));

        tv_file_name.setText(name);

    }


    public ArrayList<pictureFacer> getAllImagesByFolder(String path){
        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.ImageColumns.DATA ,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };
        Cursor cursor = GalleryActivity.this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                pictureFacer pic = new pictureFacer();
                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(pic);
            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > -1;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onPicClicked(int pos, String pictureFolderPath, String folderName) {
        Intent move = new Intent(GalleryActivity.this,GalleryViewActivity.class);
        move.putExtra("imgPath",pictureFolderPath);
        startActivity(move);
    }

}