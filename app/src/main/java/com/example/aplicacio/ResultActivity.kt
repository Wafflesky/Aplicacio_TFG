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
    private lateinit var DoB: TextView
    private lateinit var name: TextView
    private lateinit var NHC: TextView
    private lateinit var patologies: TextView

    private lateinit var region: TextView
    private lateinit var treatment: TextView
    private lateinit var desc: TextView

    private lateinit var status: TextView
    private lateinit var mobility: TextView
    private lateinit var incontinency: TextView
    private lateinit var nutrition: TextView
    private lateinit var activity: TextView
    private lateinit var emina: TextView

    private lateinit var eat: TextView
    private lateinit var bath: TextView
    private lateinit var dress: TextView
    private lateinit var tiding: TextView
    private lateinit var deposition: TextView
    private lateinit var bladder: TextView
    private lateinit var bathroom: TextView
    private lateinit var move: TextView
    private lateinit var deambulate: TextView
    private lateinit var stair: TextView
    private lateinit var barthel: TextView


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

        DoB = findViewById(R.id.dob)
        name = findViewById(R.id.name)
        NHC = findViewById(R.id.historial)
        patologies = findViewById(R.id.patologies)

        region = findViewById(R.id.regio)
        treatment = findViewById(R.id.tractament)
        desc = findViewById(R.id.bruiseDesc)

        status = findViewById(R.id.mental)
        mobility = findViewById(R.id.mobility)
        incontinency = findViewById(R.id.incontinency)
        nutrition = findViewById(R.id.nutricio)
        activity = findViewById(R.id.activity)
        emina = findViewById(R.id.resultatEmina)

        eat = findViewById(R.id.menjar)
        bath = findViewById(R.id.bany)
        dress = findViewById(R.id.vestirse)
        tiding = findViewById(R.id.arreglar)
        deposition = findViewById(R.id.deposicio)
        bladder = findViewById(R.id.miccio)
        bathroom = findViewById(R.id.labavo)
        move = findViewById(R.id.traslladar)
        deambulate = findViewById(R.id.deambulacio)
        stair = findViewById(R.id.escales)
        barthel = findViewById(R.id.resultatBarthel)
        gridView = findViewById(R.id.grid)


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

        val dobText = bitmapSingleton.getDoB()
        val nameText = bitmapSingleton.getName()
        val NHCText = bitmapSingleton.getNHC()
        val patologiesText = bitmapSingleton.getPatologies()

        val regionText = bitmapSingleton.getRegion()
        val treatmentText = bitmapSingleton.getTreatment()
        val descText = bitmapSingleton.getDesc()

        val statusText = bitmapSingleton.getStatus()
        val mobilityText = bitmapSingleton.getMobility()
        val incontinencyText = bitmapSingleton.getIncontinency()
        val nutritionText = bitmapSingleton.getNutrition()
        val activityText = bitmapSingleton.getActivity()
        val eminaText = bitmapSingleton.getEmina()

        val eatText = bitmapSingleton.getEat()
        val bathText = bitmapSingleton.getBath()
        val dressText = bitmapSingleton.getDress()
        val tidingText = bitmapSingleton.getTiding()
        val depositiontext = bitmapSingleton.getDeposition()
        val bladderText = bitmapSingleton.getBladder()
        val bathroomText = bitmapSingleton.getBathroom()
        val moveText = bitmapSingleton.getMove()
        val deambulateText = bitmapSingleton.getDeambulate()
        val stairText = bitmapSingleton.getStair()
        val barthelText = bitmapSingleton.getBarthel()

        DoB.setText(dobText)
        name.setText(nameText)
        NHC.setText(NHCText.toString())
        patologies.setText(patologiesText)

        region.setText(regionText)
        treatment.setText(treatmentText)
        desc.setText(descText)

        status.setText(statusText)
        mobility.setText(mobilityText)
        incontinency.setText(incontinencyText)
        nutrition.setText(nutritionText)
        activity.setText(activityText)

         when(eminaText) {
            0 -> emina.setText("Sense risc")
            in 1..3 -> emina.setText("Risc lleu")
            in 4..7 -> emina.setText("Risc mig")
            else -> emina.setText("Risc alt")
        }


        eat.setText(eatText)
        bath.setText(bathText)
        dress.setText(dressText)
        tiding.setText(tidingText)
        deposition.setText(depositiontext)
        bladder.setText(bladderText)
        bathroom.setText(bathroomText)
        move.setText(moveText)
        deambulate.setText(deambulateText)
        stair.setText(stairText)

        when(barthelText) {
            in 0..19 -> barthel.setText("Dependència total")
            in 20..39 -> barthel.setText("Dependència greu")
            in 40..59 -> barthel.setText("Dependència moderada")
            in 60..99 -> barthel.setText("Dependència lleu")
            else -> barthel.setText("Independència")
        }

        val images = mutableListOf<Bitmap>()
        images.add(necroticBitmap)
        images.add(grainBitmap)
        images.add(infectedBitmap)


        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridAdapter(images, context = this)

        // on below line we are setting adapter to our grid view.
        gridView.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
        }


        confirmButton.setOnClickListener{

            val intent = Intent(this, HomeActivity::class.java)
            this.startActivity(intent)

        }
    }
}


