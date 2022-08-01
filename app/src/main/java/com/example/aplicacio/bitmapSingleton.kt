package com.example.aplicacio

import android.graphics.Bitmap

object bitmapSingleton{

    init {
        println("Singleton class invoked.")
    }

    private lateinit var originalWidth: Number
    private lateinit var originalHeight: Number
    private lateinit var bitmap: Bitmap
    private lateinit var result: Bitmap
    private lateinit var grain: Bitmap
    private lateinit var necrotic: Bitmap
    private lateinit var infected: Bitmap
    private lateinit var age: Number
    private lateinit var gender: String
    private lateinit var location: String

    fun storeBitmap(bitmap: Bitmap){
        this.bitmap = bitmap
    }

    fun storeCanvasBitmap(result: Bitmap){
        this.result = result
    }

    fun storeGrainBitmap(grain: Bitmap){
        this.grain = grain
    }

    fun storeNecroticBitmap(necrotic: Bitmap){
        this.necrotic = necrotic
    }

    fun storeInfectedBitmap(infected: Bitmap){
        this.infected = infected
    }

    fun storeAge(age: Number){
        this.age = age
    }

    fun storeGender(gender: String){
        this.gender = gender
    }

    fun storeWidth(originalWidth: Number){
        this.originalWidth = originalWidth
    }

    fun storeHeight(originalHeight: Number){
        this.originalHeight = originalHeight
    }

    fun storeLocation(location: String){

        this.location = location

    }

    @JvmName("getBitmap1")
    fun getBitmap(): Bitmap {
        return bitmap
    }

    fun getCanvasBitmap(): Bitmap{
        return result
    }

    fun getGrainBitmap(): Bitmap{
        return this.grain
    }

    fun getNecroticBitmap(): Bitmap{
        return this.necrotic
    }

    fun getInfectedBitmap(): Bitmap{
        return this.infected
    }

    fun getAge(): Number{
        return this.age
    }

    fun getGender(): String{

        return this.gender

    }

    fun getLocation(): String{

        return this.location

    }

    fun getWidth(): Number{

        return this.originalWidth

    }
    fun getHeight(): Number{

        return this.originalHeight

    }




}
