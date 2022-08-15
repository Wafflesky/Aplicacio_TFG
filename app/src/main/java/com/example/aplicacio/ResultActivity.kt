package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var confirmButton: Button

    private lateinit var necroticBitmap: Bitmap
    private lateinit var grainBitmap: Bitmap
    private lateinit var infectedBitmap: Bitmap

    private lateinit var necroImage: ImageView
    private lateinit var grainImage: ImageView
    private lateinit var infImage: ImageView

    private lateinit var patient: TextView
    private lateinit var age: TextView
    private lateinit var gender: TextView

    private lateinit var patiengGender: String
    private lateinit var patientAge: Number

    private lateinit var gridView: GridView

    //private lateinit var originalWidth: Number
    //private lateinit var originalHeight: Number

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_result, null)

        //val originalWidth = bitmapSingleton.getWidth()
        //val originalHeight = bitmapSingleton.getHeight()

        confirmButton = findViewById(R.id.Confirm_info_button)
        //patient = findViewById(R.id.patient)
        //age = findViewById(R.id.personal_age)
        //gender = findViewById(R.id.personal_gender)

        //gridView = findViewById(R.id.grid)


        necroticBitmap = bitmapSingleton.getNecroticBitmap()
        //necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap, originalWidth as Int, originalHeight as Int, false)

        grainBitmap = bitmapSingleton.getGrainBitmap()
        //grainBitmap = Bitmap.createScaledBitmap(grainBitmap, originalWidth as Int, originalHeight as Int, false)

        infectedBitmap = bitmapSingleton.getInfectedBitmap()
        //infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap, originalWidth as Int, originalHeight as Int, false)

        //necroImage.setImageBitmap(necroticBitmap)
        //grainImage.setImageBitmap(grainBitmap)
        //infImage.setImageBitmap(infectedBitmap)

        //TODO: Aixo en lloc d'agafar.ho del singleton ho agafarem del usuari que hagi triat
        //patientAge = bitmapSingleton.getAge()
        //patiengGender = bitmapSingleton.getGender()

        patientAge = 67
        patiengGender = "Female"

        //patient.setText("Patient 0001")
        //age.setText(patientAge.toString())
        //gender.setText(patiengGender)

        val images = mutableListOf<Bitmap>()
        images.add(necroticBitmap)
        images.add(grainBitmap)
        images.add(infectedBitmap)


        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridAdapter(images, context = this)

        // on below line we are setting adapter to our grid view.
        //gridView.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        //gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            //Toast.makeText(
                //applicationContext, images[position].courseName + " selected",
                //Toast.LENGTH_SHORT
            //).show()
        //}


        confirmButton.setOnClickListener{

            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)

        }
    }
}