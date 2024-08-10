package com.example.gpsmaponphoto.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;

public class Storage {
    public static String CreateFolder(String str, String str2) {
        if (Build.VERSION.SDK_INT <= 29) {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + str + "/" + str2);
            String sb = "createDir:" +
                    file;
            Log.d("TAG", sb);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        File file2 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + str + File.separator + str2)));
        String sb2 = "createDir:" +
                file2;
        Log.d("TAG", sb2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.getAbsolutePath();
    }
    public static String saveimage(Context context, Bitmap bitmap, String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str2);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            String absolutePath = file2.getAbsolutePath();
            MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, (String[]) null, (MediaScannerConnection.OnScanCompletedListener) null);
            return absolutePath;
        } catch (Exception unused) {
            return null;
        }
    }



}
