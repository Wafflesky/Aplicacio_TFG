package com.example.aplicacio


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration


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
    lateinit var currentPhotoPath: String

    @SuppressLint("InflateParams")
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
                //TODO: Canviar asterisc per *
                .setAction(Intent.ACTION_PICK)

            getResult.launch(intent)
        }

        //Take picture with phone camera
        newPictureButton.setOnClickListener{

            dispatchTakePictureIntent()

        }


        //Draw a Mask
        dataBaseButton.setOnClickListener {

            /*
            if(imageUri != null) {
                val bitmap = getCapturedImage(imageUri!!)


                bitmapSingleton.storeBitmap(bitmap)
                val intent = Intent(this, SecondActivity::class.java)
                //intent.putExtra("image", byteArray)
                this.startActivity(intent)
            }
            */

            val intent = Intent(this, UserActivity::class.java)
            this.startActivity(intent)

        }
        /*
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        /*
         */
        val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        fun hasNoPermissions(): Boolean{
            return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission(){
            ActivityCompat.requestPermissions(this, permissions,0)
        }
    */

    }

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
                //imageView.setImageURI(imageUri)
                //val drawable = imageView.drawable as BitmapDrawable
                //val bitmap = drawable.bitmap
                //markButtonEnable(button3)
                //markButtonEnable(button4)
            }
        }
    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    //Converts into Bitmap
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
            //imageView.setImageURI(imageUri)
            //markButtonEnable(button3)
            //markButtonEnable(button4)
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bitmapSingleton.storeBitmap(imageBitmap)
            bitmapSingleton.storeWidth(imageBitmap.width)
            bitmapSingleton.storeHeight(imageBitmap.height)
            val intent = Intent(this, ImageActivity::class.java)
            this.startActivity(intent)
            //imageView.setImageBitmap(imageBitmap)
            //markButtonEnable(button3)
            //markButtonEnable(button4)
        }


    }
}