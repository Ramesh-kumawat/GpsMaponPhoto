package com.example.gpsmaponphoto;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class collectionActivity extends AppCompatActivity {

     ImageView Back_collection_btn,share_btn,delete_btn,img_show;

    int id;
    Dialog dialog;
    ImageView undelete_btn_dialog, delete_btn_dialog;
    ImageView photo;
    LinearLayout main_box;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        share_btn = findViewById(R.id.share_btn);
        delete_btn = findViewById(R.id.delete_btn);
        img_show = findViewById(R.id.img_show);
        Back_collection_btn = findViewById(R.id.Back_collection_btn);

         dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_for_delete);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


        delete_btn_dialog = dialog.findViewById(R.id.save);
        undelete_btn_dialog = dialog.findViewById(R.id.unsave);
        photo = dialog.findViewById(R.id.photo);
        main_box = dialog.findViewById(R.id.main_box);

        size();





        Back_collection_btn.setOnClickListener(view -> {
            onBackPressed();
        });


        Intent intent = getIntent();
        String path = intent.getStringExtra("lastimagepath");
        String path2 = intent.getStringExtra("imgPath");

        id = intent.getIntExtra("id", 0);

        share_btn.setOnClickListener(view -> {
            if(id == 1){

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(path2));
                startActivity(Intent.createChooser(share, "Share Image"));
            }else{

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });

        if (id == 1) {

            if (path2 != null) {
                File imgFile = new File(path2);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    img_show.setImageBitmap(myBitmap);
                }
            }
        } else {
            if(path != null){
            File imgFile = new File(path);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            if (myBitmap != null) {
                img_show.setImageBitmap(myBitmap);
            }
            }

        }



        delete_btn.setOnClickListener(view -> {


            dialog.show();

            delete_btn_dialog.setOnClickListener(view12 -> {

                if(id == 1) {
                    assert path2 != null;
                    File fdelete = new File(path2);

                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            System.out.println("file Deleted: "+path2);
                            Log.d("TAG", "onClick: "+id);
                        } else {
                            System.out.println("file not Deleted: " +path2);
                        }
                    }
                    Toast.makeText(collectionActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(collectionActivity.this, AllCollectionImages.class);
                    intent1.putExtra("path", path2);
                    setResult(108, intent1);
                    finish();

                }else{
                    assert path != null;
                    File fdelete = new File(path);
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            System.out.println("file Deleted :" + path);
                        } else {
                            System.out.println("file not Deleted :" + path);
                        }
                    }
                    onBackPressed();
                }
                dialog.dismiss();
            });

            undelete_btn_dialog.setOnClickListener(view1 -> dialog.dismiss());

        });
    }

    private void size() {

        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(share_btn,47,59);
        HelperResizer.setMargin(share_btn,0,0,70,0);

        HelperResizer.setSize(delete_btn,48,55);
        HelperResizer.setMargin(delete_btn,0,0,65,0);

        HelperResizer.setSize(Back_collection_btn,60,65);
        HelperResizer.setPadding(Back_collection_btn,10,10,10,10);
        HelperResizer.setMargin(Back_collection_btn,70,0,0,0);

        HelperResizer.setSize(delete_btn_dialog,320,115);

        HelperResizer.setSize(undelete_btn_dialog,320,115);
        HelperResizer.setMargin(undelete_btn_dialog,0,0,50,0);

        HelperResizer.setSize(photo,89,82);
        HelperResizer.setMargin(photo,0,40,0,0);

        HelperResizer.setSize(main_box,858,570);
    }


    @Override
    public void onBackPressed() {
        MediaScannerConnection.scanFile(this, new String[] { Environment.getExternalStorageDirectory().toString() + "/Gps Map on Photo/Images" }, null, (path, uri) -> {
            Log.i("ExternalStorage", "Scanned " + path + ":");
            Log.i("ExternalStorage", "-> uri=" + uri);
        });

        if(id != 1) {
            Intent intent = new Intent(collectionActivity.this, AllCollectionImages.class);
            startActivity(intent);
            finish();
        }else{
            super.onBackPressed();
        }



    }
}