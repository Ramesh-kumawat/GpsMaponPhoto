package com.example.gpsmaponphoto;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsmaponphoto.Adapters.picture_Adapter;
import com.example.gpsmaponphoto.Models.pictureFacer;

import java.util.ArrayList;
import java.util.Iterator;

public class AllCollectionImages extends AppCompatActivity implements picture_Adapter.onitemClick {

     RecyclerView recyclerView;
     ArrayList<pictureFacer> imgs = new ArrayList<>();
    ImageView all_collection_back;
    picture_Adapter picture_adapter;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_collection_images);

        recyclerView =  findViewById(R.id.Collection_images_gallery);
        all_collection_back = findViewById(R.id.all_collection_back);


        HelperResizer.getheightandwidth(this);
        HelperResizer.setSize(all_collection_back,60,65);
        HelperResizer.setPadding(all_collection_back,10,10,10,10);
        HelperResizer.setMargin(all_collection_back,70,0,0,0);


        imgs =  getAllImagesByFolder("/Gps Map on Photo/Images");

        picture_adapter = new picture_Adapter(imgs,this,this);
        recyclerView.setAdapter(picture_adapter);

        all_collection_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public ArrayList<pictureFacer> getAllImagesByFolder(String path){


        imgs.clear();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.ImageColumns.DATA ,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };

        MediaScannerConnection.scanFile(this, new String[] { Environment.getExternalStorageDirectory().toString() + "/Gps Map on Photo/Images" }, null, (path1, uri) -> {
            Log.i("ExternalStorage", "Scanned " + path1 + ":");
            Log.i("ExternalStorage", "-> uri=" + uri);
        });

        Cursor cursor  = getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);

        Log.d("TAG", "getAllImagesByFolder: "+cursor.getCount());
        try {
            cursor.moveToFirst();
            do{
                pictureFacer pic = new pictureFacer();
                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                imgs.add(pic);
            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = imgs.size()-1;i > -1;i--){
                Log.d("TAG", "getAllImagesByFolder: "+imgs.size());
                reSelection.add(imgs.get(i));
            }
            imgs = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgs;
    }

    @Override
    public void onPicClicked(int pos, String pictureFolderPath, String folderName) {
        this.path = pictureFolderPath;
        Intent move = new Intent(AllCollectionImages.this,collectionActivity.class);
        move.putExtra("imgPath",pictureFolderPath);
        move.putExtra("id",1);
        startActivityForResult(move, 108);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 108) {
            if (data != null) {
                String s = data.getStringExtra("path");
                Iterator<pictureFacer> images = imgs.iterator();
                while (images.hasNext()) {
                    pictureFacer pf = images.next();
                    if (pf.getPicturePath().equals(s)) {
                        images.remove();
                    }
                }
                picture_adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        picture_adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }
}