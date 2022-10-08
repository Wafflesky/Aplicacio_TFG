package com.example.aplicacio.ViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.ui.AppBarConfiguration
import com.example.aplicacio.Model.bitmapSingleton
import com.example.aplicacio.R
import org.bytedeco.librealsense.context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


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
    private lateinit var mCurrentPhotoPath: String
    //Camera Related
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_TAKE_PHOTO = 2

    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

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
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null){

            val photoFile = createImageFile()


            if(photoFile != null){
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "com.example.aplicacio.provider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO)
            }

        }
    }

    private fun createImageFile() : File {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            timeStamp,
            ".jpg",
            storageDir
        )
        Log.i("Path",image.absolutePath)
        mCurrentPhotoPath = image.absolutePath
        return image
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
    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("Result","Enter")
        Log.i("ResultCode", resultCode.toString())
        Log.i("RequestCode", requestCode.toString())
        Log.i("Result_Ok", RESULT_OK.toString())
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

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.i("Balls","Balls")
            val fileImage = File(mCurrentPhotoPath)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,Uri.fromFile(fileImage))
            if (bitmap != null) {

                bitmapSingleton.storeBitmap(bitmap)
                bitmapSingleton.storeWidth(bitmap.width)
                bitmapSingleton.storeHeight(bitmap.height)

                val intent = Intent(this, ImageActivity::class.java)
                this.startActivity(intent)

            }
            val file = File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg")


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
