package com.example.aplicacio.Model;

import android.graphics.Bitmap;

import org.jetbrains.annotations.Nullable;

public class BruiseImagesData {

    Bitmap[] bruises;


    private BruiseImagesData(Bitmap[] bruises) {
        this.bruises = bruises;
    }

    @Nullable
    public Bitmap get(int position) {
        return bruises[position];
    }
}
