package com.example.aplicacio.ViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.aplicacio.Model.bitmapSingleton
import com.example.aplicacio.R

/**
 * Classe on l'usuari es mourà per a utiltizar les funcionalitats de la aplicació
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var newPictureButton: Button
    private lateinit var existingPictureButton: Button
    private lateinit var dataBaseButton: Button
    private val pickImage = 100
    private val cameraRequest = 1888
    private var imageUri: Uri? = null
    //Camera Related
    val REQUEST_IMAGE_CAPTURE = 1

    @SuppressLint("InflateParams")
    /**
     * Funció que es crida a l'hora de crear la classe, aqui connectem els elements de la vista amb
     * el codi i afegim listeners als botons per a que aquests fagin les funcionalitats que volem
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_main, null)
        newPictureButton = findViewById(R.id.button2)
        existingPictureButton = findViewById(R.id.button1)
        dataBaseButton = findViewById(R.id.button3)

        existingPictureButton.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_PICK)

            getResult.launch(intent)
        }

        //Take picture with phone camera
        newPictureButton.setOnClickListener{

            dispatchTakePictureIntent()

        }

        //Afegir la icona i posar el codi correcte per a fer foto be

        //Draw a Mask
        dataBaseButton.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
            this.startActivity(intent)

        }


    }

    /**
     * Aqui s'aconsegueix la imatge que s'ha fet amb la camera i ens porta a la pantalla de confirmació d'aquesta
     */
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val data: Intent? = it.data
                imageUri = data?.data
                val bitmap = imageUri?.let { it1 -> getCapturedImage(it1) }
                if (bitmap != null) {
                    bitmapSingleton.storeBitmap(bitmap)
                    val intent = Intent(this, ImageActivity::class.java)
                    this.startActivity(intent)
                }

            }
        }

    /**
     * Funció que demana accedir a la camera del telèfon
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    /**
     * Funció que converteix la imatge adquirida a bitmap per a poder tractar-la posteriorment
     */
    private fun getCapturedImage(selectedPhotoUri: Uri): Bitmap {
        val bitmap = when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return bitmap
    }

    /**
     * Funció que comprova si les activitats de fer foto o seleccionar foto s'han acabat
     * i que actua depenent del cas
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val bitmap = imageUri?.let { getCapturedImage(it) }
            if (bitmap != null) {
                bitmapSingleton.storeBitmap(bitmap)
                bitmapSingleton.storeWidth(bitmap.width)
                bitmapSingleton.storeHeight(bitmap.height)
            }
            val intent = Intent(this, ImageActivity::class.java)
            this.startActivity(intent)

        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bitmapSingleton.storeBitmap(imageBitmap)
            bitmapSingleton.storeWidth(imageBitmap.width)
            bitmapSingleton.storeHeight(imageBitmap.height)
            val intent = Intent(this, ImageActivity::class.java)
            this.startActivity(intent)

        }


    }

    /**
     * Funció que evita que l'usuari torni a la pantalla del login o a la pantalla de resultats.
     */
    override fun onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(this@HomeActivity, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }
}