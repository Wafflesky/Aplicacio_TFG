package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageActivity: AppCompatActivity() {

    private lateinit var confirmButton: Button
    private lateinit var cancelButton: Button
    private lateinit var image: ImageView
    private lateinit var imageBitmap: Bitmap

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_image, null)
        confirmButton = findViewById(R.id.button5)
        cancelButton = findViewById(R.id.button4)
        image= findViewById(R.id.imageView2)
        imageBitmap = bitmapSingleton.getBitmap()
        image.setImageBitmap(imageBitmap)

        val width = imageBitmap.width
        val height = imageBitmap.height

        bitmapSingleton.storeWidth(width)
        bitmapSingleton.storeHeight(height)

        confirmButton.setOnClickListener{

            val intent = Intent(this, CanvasActivity::class.java)
            this.startActivity(intent)

        }
        cancelButton.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)

        }
    }
}