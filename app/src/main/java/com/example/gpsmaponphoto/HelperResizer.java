package com.example.gpsmaponphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

public class HelperResizer {
    public static int SCALE_HEIGHT = 1920;
    public static int SCALE_WIDTH = 1080;
    public static int height;
    public static int width;

    public static void getheightandwidth(Context context) {
        getHeight(context);
        getwidth(context);
    }

    public static int getwidth(Context context) {
        width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static void setHeight(Context context, View view, int i) {
        view.getLayoutParams().height = (context.getResources().getDisplayMetrics().heightPixels * i) / SCALE_HEIGHT;
    }

    public static void setWidth(Context context, View view, int i) {
        view.getLayoutParams().width = (context.getResources().getDisplayMetrics().widthPixels * i) / SCALE_WIDTH;
    }

    public static int setHeight(int i) {
        return (height * i) / 1920;
    }

    public static int setWidth(int i) {
        return (width * i) / 1080;
    }

    public static void setSize(View view, int i, int i2) {
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setWidth(i);
    }

    public static void setHeightWidthAsWidth(Context context, View view, int i, int i2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i3 = (displayMetrics.widthPixels * i) / SCALE_WIDTH;
        int i4 = (displayMetrics.widthPixels * i2) / SCALE_WIDTH;
        view.getLayoutParams().width = i3;
        view.getLayoutParams().height = i4;
    }

    public static void setSize(View view, int i, int i2, boolean z) {
        if (z) {
            view.getLayoutParams().height = setWidth(i2);
            view.getLayoutParams().width = setWidth(i);
            return;
        }
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setHeight(i);
    }

    public static void setMargin(View view, int i, int i2, int i3, int i4) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(setWidth(i), setHeight(i2), setWidth(i3), setHeight(i4));
    }

    public static void setPadding(View view, int i, int i2, int i3, int i4) {
        view.setPadding(i, i2, i3, i4);
    }
    public static Bitmap bitmapResize(Bitmap bitmap, int i, int i2) {
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();
        if (width2 >= height2) {

            int i3 = (height2 * i) / width2;
            if (i3 > i2) {
                i = (i * i2) / i3;
            } else {
                i2 = i3;
            }
        } else {
            int i4 = (width2 * i2) / height2;
            if (i4 > i) {
                i2 = (i2 * i) / i4;
            } else {
                i = i4;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getImages(Context context, String str) {
        if (context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data like?", new String[]{"%" + str + "%"}, "date_modified DESC");
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        @SuppressLint("Range") String string = query.getString(query.getColumnIndex("_data"));
        query.close();
        return string;
    }
}
