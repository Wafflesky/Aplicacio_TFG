package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.Model.bitmapSingleton
import com.example.aplicacio.R
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class UserDataActivity: AppCompatActivity() {

    private lateinit var confirmButton: Button

    private lateinit var necroticBitmap: Bitmap
    private lateinit var grainBitmap: Bitmap
    private lateinit var infectedBitmap: Bitmap

    private lateinit var necroticUrl: String
    private lateinit var grainUrl: String
    private lateinit var infectedUrl: String

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

    private var pacient = Patient()


    private lateinit var patiengGender: String
    private lateinit var patientAge: Number

    private lateinit var gridView: GridView
    private lateinit var selectedNHC: String
    private lateinit var selectedEntry: String
    private var selectedNumberEntry: Int = 0

    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")

    private val images = mutableListOf<String>()

    private val storage = Firebase.storage

    //private lateinit var originalWidth: Number
    //private lateinit var originalHeight: Number

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_userinfo, null)

        //Fer la informació més gran

        populateView()
        fillInformation()

        val myRef = mDatabase.getReference()
        myRef.child("Patients").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    pacient = Patient()

                    val nhc = jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    val patient = jobSnapshot.child("patient").getValue(Patient::class.java)
                    Log.i("NHC", nhc)
                    Log.i("Patient", patient.toString())

                    if(nhc.equals(selectedNHC)){
                        if (patient != null) {
                            if (patient.entryNumber == (selectedNumberEntry -1)) {
                                pacient = patient
                                populateView()

                            }
                        }
                    }

                        //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                            Log.i("firebase", "Got value ${patient}")
                    }

                }
                // go to next step from here e.g handlePosts(posts);
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(javaClass.name.toString(), ": " + databaseError.message)
            }
        })

        //TODO: Aixo en lloc d'agafar.ho del singleton ho agafarem del usuari que hagi triat



    }

    fun populateView(){

        selectedNHC = bitmapSingleton.getNHC()
        selectedNumberEntry = bitmapSingleton.getNHCEntries()

        //Log.i("nhc",selectedEntry)

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

    }

    fun fillInformation() {

        DoB.setText(pacient.DoB)
        name.setText(pacient.name)
        NHC.setText(selectedNHC)
        patologies.setText(pacient.patologies)

        region.setText(pacient.bruiseData.region)
        treatment.setText(pacient.bruiseData.treatment)
        desc.setText(pacient.bruiseData.bruiseDesc)

        status.setText(pacient.emina.mentalStatus)
        mobility.setText(pacient.emina.mobility)
        incontinency.setText(pacient.emina.incontinency)
        nutrition.setText(pacient.emina.nutrition)
        activity.setText(pacient.emina.activity)

        Log.i("emina", pacient.emina.eminaResult)

        val eminaText = pacient.emina.eminaResult.toInt()

        when (eminaText) {
            0 -> emina.setText("Sense risc")
            in 1..3 -> emina.setText("Risc lleu")
            in 4..7 -> emina.setText("Risc mig")
            else -> emina.setText("Risc alt")
        }


        eat.setText(pacient.barthel.eat)
        bath.setText(pacient.barthel.bath)
        dress.setText(pacient.barthel.dress)
        tiding.setText(pacient.barthel.tiding)
        deposition.setText(pacient.barthel.deposition)
        bladder.setText(pacient.barthel.bladder)
        bathroom.setText(pacient.barthel.bathroom)
        move.setText(pacient.barthel.move)
        deambulate.setText(pacient.barthel.deambulate)
        stair.setText(pacient.barthel.stair)

        val barthelText = pacient.barthel.barthelResult.toInt()

        when (barthelText) {
            in 0..19 -> barthel.setText("Dependència total")
            in 20..39 -> barthel.setText("Dependència greu")
            in 40..59 -> barthel.setText("Dependència moderada")
            in 60..99 -> barthel.setText("Dependència lleu")
            else -> barthel.setText("Independència")
        }

        images.add(pacient.necroticImage)
        images.add(pacient.grainImage)
        images.add(pacient.infectedImage)

        if (!images.isEmpty()) {

            FirebaseApp.initializeApp(/*context=*/this)
            val firebaseAppCheck = FirebaseAppCheck.getInstance()
            firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance()
            )


            // on below line we are initializing our course adapter
            // and passing course list and context.
            val courseAdapter = GridAdapter(images,null, this@UserDataActivity,true)

            // on below line we are setting adapter to our grid view.
            gridView.adapter = courseAdapter

            // on below line we are adding on item
            // click listener for our grid view.
            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                // inside on click method we are simply displaying
                // a toast message with course name.

            }

        }
    }
}