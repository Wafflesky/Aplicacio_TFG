package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aplicacio.Model.bitmapSingleton
import com.example.aplicacio.R
import org.bytedeco.librealsense.context

/**
 * Classe que apareix un cop es clica un dels items dins dels gridViews que trobem en el
 * ResultActivity i en el UserDataActivity
 */
class ViewImageActivity: AppCompatActivity() {


    private lateinit var image: ImageView
    private lateinit var ulcerType: TextView
    private lateinit var imageBitmap: Bitmap

    @SuppressLint("InflateParams")
    /**
     * Funció que es crida un cop es crea la classe, aqui agafem el valor que s'ha clicat en el
     * gridview i l'utilitzem per a mostrar la imatge corresponent
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_image, null)
        val position = intent.getIntExtra("position", 0)
        val imageURL = intent.getStringExtra("stringImage")
        image = findViewById(R.id.ulcerImage)
        ulcerType = findViewById(R.id.ulcerType)


        if (position != null) {

            when (position) {
                0 -> ulcerType.text = "Necrotica"
                1 -> ulcerType.text = "Granulosa"
                2 -> ulcerType.text = "Infectada"

            }
        }

        if(imageURL != null){

            //Amb aquesta funció podem agafar la imatge que s'ha guardat en el firebase Storage i
            //accedir-hi fàcilment
            Glide
                .with(applicationContext)
                .load(imageURL)
                .into(image)
        }
        else{
            imageBitmap = bitmapSingleton.getBitmap()
            image.setImageBitmap(imageBitmap)
        }
    }

    }

