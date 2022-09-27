package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacio.R
import com.example.aplicacio.Model.bitmapSingleton

/**
 * Classe on l'usuari confirma la imatge seleccionada
 */
class ImageActivity: AppCompatActivity() {

    private lateinit var confirmButton: Button
    private lateinit var cancelButton: Button
    private lateinit var image: ImageView
    private lateinit var imageBitmap: Bitmap

    @SuppressLint("InflateParams")
    /**
     * Funció que es crida un cop es crea la classe i es on s'inicialitza les variables.
     * També es creen els listeners dels botons per a tornar enrere o seguir amb la execució
     */
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