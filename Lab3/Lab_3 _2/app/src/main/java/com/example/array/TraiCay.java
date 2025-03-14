package com.example.array;

import android.graphics.Bitmap;

public class TraiCay {
    private String ten;
    private String mota;
    private int hinh; // For drawable resources
    private Bitmap bitmapImage; // For custom images

    // Constructor for drawable resource
    public TraiCay(String ten, String mota, int hinh) {
        this.ten = ten;
        this.mota = mota;
        this.hinh = hinh;
        this.bitmapImage = null;
    }

    // Constructor for bitmap image
    public TraiCay(String ten, String mota, Bitmap bitmapImage) {
        this.ten = ten;
        this.mota = mota;
        this.bitmapImage = bitmapImage;
        this.hinh = 0; // No drawable resource used
    }

    // Getters and setters
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }
}




