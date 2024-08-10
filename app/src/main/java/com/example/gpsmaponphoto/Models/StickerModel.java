package com.example.gpsmaponphoto.Models;

public class StickerModel {

    int layout;
    int imglayout;

    public int getImglayout() {
        return imglayout;
    }

    public StickerModel(int layout, int imglayout) {
        this.layout = layout;
        this.imglayout = imglayout;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}
