package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

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

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_result, null)

        confirmButton = findViewById(R.id.Confirm_info_button)
        patient = findViewById(R.id.patient)
        age = findViewById(R.id.personal_age)
        gender = findViewById(R.id.personal_gender)

        necroImage = findViewById(R.id.Necro)
        grainImage = findViewById(R.id.Grain)
        infImage = findViewById(R.id.Infec)

        //gridView = findViewById(R.id.grid)


        necroticBitmap = bitmapSingleton.getNecroticBitmap()
        grainBitmap = bitmapSingleton.getGrainBitmap()
        infectedBitmap = bitmapSingleton.getInfectedBitmap()

        necroImage.setImageBitmap(necroticBitmap)
        grainImage.setImageBitmap(grainBitmap)
        infImage.setImageBitmap(infectedBitmap)

        patientAge = bitmapSingleton.getAge()
        patiengGender = bitmapSingleton.getgGender()

        patient.setText("Patient 0001")
        age.setText(patientAge.toString())
        gender.setText(patiengGender)


        confirmButton.setOnClickListener{

            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)

        }
    }
}