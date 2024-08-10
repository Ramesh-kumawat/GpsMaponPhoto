package com.example.gpsmaponphoto.Models;

public class pictureFacer {

     String picturName;
     String picturePath;
      String pictureSize;


    public pictureFacer(){

    }

    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }


    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

}
