package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.example.aplicacio.*
import com.example.aplicacio.Model.*
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Classe on es mostra la informació resultant del pacient
 */
class ResultActivity : AppCompatActivity() {

    private lateinit var confirmButton: Button
    private lateinit var progressbar: ProgressBar
    private lateinit var cardEmina: CardView
    private lateinit var cardBarthel: CardView

    private val images = mutableListOf<Bitmap>()

    private lateinit var necroticBitmap: Bitmap
    private lateinit var grainBitmap: Bitmap
    private lateinit var infectedBitmap: Bitmap

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
    private lateinit var eminaResult: TextView

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
    private lateinit var barthelResult: TextView

    private lateinit var dobText: String
    private lateinit var nameText: String
    private lateinit var NHCText: String
    private lateinit var patologiesText: String

    private lateinit var regionText: String
    private lateinit var treatmentText: String
    private lateinit var descText: String

    private lateinit var emina: Emina
    private lateinit var statusText: String
    private lateinit var mobilityText: String
    private lateinit var incontinencyText: String
    private lateinit var nutritionText: String
    private lateinit var activityText: String
    private lateinit var eminaText: Number

    private lateinit var barthel: Barthel
    private lateinit var eatText: String
    private lateinit var bathText: String
    private lateinit var dressText: String
    private lateinit var tidingText: String
    private lateinit var depositiontext: String
    private lateinit var bladderText: String
    private lateinit var bathroomText: String
    private lateinit var moveText: String
    private lateinit var deambulateText: String
    private lateinit var stairText: String
    private lateinit var barthelText: Number

    private lateinit var gridView: GridView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "InflateParams")
    /**
     * Funció que es crida quan s'inicia la classe. Aqui es carrega la informació del singleton i s'inicialitzen les variables.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_result, null)

        //Arreglar visibilitat + Posar una barra de carrega

        populateView()
        progressbar.visibility = View.GONE

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()

        necroticBitmap = bitmapSingleton.getNecroticBitmap()
        necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap,
            originalWidth as Int, originalHeight as Int, false)

        grainBitmap = bitmapSingleton.getGrainBitmap()
        grainBitmap = Bitmap.createScaledBitmap(grainBitmap, originalWidth, originalHeight, false)

        infectedBitmap = bitmapSingleton.getInfectedBitmap()
        infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap, originalWidth, originalHeight, false)

        fillInformation()

        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridAdapter(null,images, context = this,false)

        // on below line we are setting adapter to our grid view.
        gridView.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            bitmapSingleton.storeBitmap(images.get(position))
            val intent = Intent(this@ResultActivity, ViewImageActivity::class.java)
            intent.putExtra("position", position);
            startActivity(intent)

            // inside on click method we are simply displaying
            // a toast message with course name.
        }


        confirmButton.setOnClickListener{


            // show the visibility of progress bar to show loading
            progressbar.visibility = View.VISIBLE

            val database = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")
            val storage = Firebase.storage
            val myRef = database.getReference().child("Patients").push()

            val storageRef = storage.reference

            val necroticBaos = ByteArrayOutputStream()
            val infectedBaos = ByteArrayOutputStream()
            val grainBaos = ByteArrayOutputStream()

            var necroticUUID = UUID.randomUUID().toString()
            var grainUUID = UUID.randomUUID().toString()
            var infectedUUID = UUID.randomUUID().toString()

            necroticBitmap.compress(Bitmap.CompressFormat.PNG, 100, necroticBaos)
            grainBitmap.compress(Bitmap.CompressFormat.PNG, 100, grainBaos)
            infectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, infectedBaos)

            //create a file to write bitmap data

            val necroticData = necroticBaos.toByteArray()

            FirebaseApp.initializeApp(/*context=*/this)
            val firebaseAppCheck = FirebaseAppCheck.getInstance()
            firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance()
            )

            val necroticReference = storageRef.child("Necrotic/" + necroticUUID + ".png")
            val uploadNecro = necroticReference.putBytes(necroticData)

            uploadNecro.addOnCompleteListener {

                necroticReference.downloadUrl.addOnSuccessListener {
                    necroticUUID= it.toString()

                    val grainData = grainBaos.toByteArray()

                    val grainReference = storageRef.child("Grain/" + grainUUID + ".png")
                    val uploadGrain = grainReference.putBytes(grainData)
                    uploadGrain.addOnCompleteListener {

                        grainReference.downloadUrl.addOnSuccessListener {
                            grainUUID = it.toString()

                            val infectedData = infectedBaos.toByteArray()

                            val infectedReference = storageRef.child("Infected/" + infectedUUID + ".png")
                            val uploadInfected = infectedReference.putBytes(infectedData)
                            uploadInfected.addOnCompleteListener {

                                infectedReference.downloadUrl.addOnSuccessListener {
                                    infectedUUID = it.toString()

                                    val entryNumber = bitmapSingleton.getNHCEntriesCreation()


                                    val current = LocalDateTime.now()
                                    val formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy")
                                    val formatted = current.format(formatter)

                                    val bruiseInfo = BruiseData(regionText, treatmentText, descText)
                                    val barthelInfo = Barthel(
                                        bathText,
                                        bathroomText,
                                        bladderText,
                                        deambulateText,
                                        depositiontext,
                                        dressText,
                                        eatText,
                                        moveText,
                                        stairText,
                                        tidingText,
                                        barthelText.toString()
                                    )
                                    val eminaInfo = Emina(
                                        activityText,
                                        incontinencyText,
                                        statusText,
                                        mobilityText,
                                        nutritionText,
                                        eminaText.toString()
                                    )
                                    val patientInfo = Patient(
                                        dobText,
                                        name = nameText,
                                        entryNumber = entryNumber,
                                        patologies = patologiesText,
                                        bruiseData = bruiseInfo,
                                        barthel = barthelInfo,
                                        emina = eminaInfo,
                                        necroticImage = necroticUUID,
                                        grainImage = grainUUID,
                                        infectedImage = infectedUUID,
                                        dataEnregistrament = formatted
                                    )

                                    val nhc = bitmapSingleton.getNHC()
                                    val history = UserActivity.Historial(patientInfo, nhc)

                                    print(patientInfo)

                                    myRef.setValue(
                                        history,
                                        object : DatabaseReference.CompletionListener {
                                            @SuppressLint("RestrictedApi")
                                            override fun onComplete(
                                                error: DatabaseError?,
                                                ref: DatabaseReference
                                            ) {
                                                if (error != null) {
                                                    Log.v(
                                                        LOG_TAG,
                                                        "Data could not be saved. " + error.getMessage()
                                                    ) //Hits here every time.
                                                } else {
                                                    Log.v(
                                                        LOG_TAG,
                                                        "Data saved successfully. Finishing activity..."
                                                    )
                                                    progressbar.visibility = View.GONE
                                                    val intent = Intent(this@ResultActivity, HomeActivity::class.java)
                                                    startActivity(intent)
                                                }
                                            }

                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Funció que connecta els elements del codi amb els corresponents elements de la vista mitjançant els identificadors
     */
    fun populateView(){

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
        eminaResult = findViewById(R.id.resultatEmina)

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
        barthelResult = findViewById(R.id.resultatBarthel)
        gridView = findViewById(R.id.grid)

        progressbar = findViewById<ProgressBar>(R.id.progressBarResult)
        cardEmina = findViewById(R.id.cardView)
        cardBarthel = findViewById(R.id.cardView2)

    }

    /**
     * Funció que afegeix la informació del usuari dins dels elements. També es calcula els
     * resultats de les escales EMINA i Barthel i es mostra un text i color depenent d'aquests
     */
    fun fillInformation(){

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()

        necroticBitmap = bitmapSingleton.getNecroticBitmap()
        necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap,
            originalWidth as Int, originalHeight as Int, false)

        grainBitmap = bitmapSingleton.getGrainBitmap()
        grainBitmap = Bitmap.createScaledBitmap(grainBitmap, originalWidth, originalHeight, false)

        infectedBitmap = bitmapSingleton.getInfectedBitmap()
        infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap, originalWidth, originalHeight, false)

        dobText = bitmapSingleton.getDoB()
        nameText = bitmapSingleton.getName()
        NHCText = bitmapSingleton.getNHC()
        patologiesText = bitmapSingleton.getPatologies()

        regionText = bitmapSingleton.getRegion()
        treatmentText = bitmapSingleton.getTreatment()
        descText = bitmapSingleton.getDesc()

        emina = bitmapSingleton.getEmina()

        statusText = emina.mentalStatus
        mobilityText = emina.mobility
        incontinencyText = emina.incontinency
        nutritionText = emina.nutrition
        activityText = emina.activity
        eminaText = bitmapSingleton.getEminaResult()

        barthel = bitmapSingleton.getBarthel()

        eatText = barthel.eat
        bathText = barthel.bath
        dressText = barthel.dress
        tidingText = barthel.tiding
        depositiontext = barthel.deposition
        bladderText = barthel.bladder
        bathroomText = barthel.bathroom
        moveText = barthel.move
        deambulateText = barthel.deambulate
        stairText = barthel.stair

        barthelText = bitmapSingleton.getBarthelResult()

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
            0 -> {
                eminaResult.setText("Sense risc")
                cardEmina.setCardBackgroundColor(Color.argb(100,0, 200, 0))
            }
            in 1..3 -> {
                eminaResult.setText("Risc lleu")
                cardEmina.setCardBackgroundColor(Color.argb(100,100, 160, 0))
            }
            in 4..7 -> {
                eminaResult.setText("Risc mig")
                cardEmina.setCardBackgroundColor(Color.argb(100,255, 160, 0))
            }
            else -> {
                eminaResult.setText("Risc alt")
                cardEmina.setCardBackgroundColor(Color.argb(100,255, 0, 0))
            }
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
            in 0..19 -> {
                barthelResult.setText("Independència")
                cardBarthel.setCardBackgroundColor(Color.argb(100,0, 200, 0))
            }
            in 20..39 -> {
                barthelResult.setText("Dependència lleu")
                cardBarthel.setCardBackgroundColor(Color.argb(100,75, 200, 0))
            }
            in 40..59 -> {
                barthelResult.setText("Dependència moderada")
                cardBarthel.setCardBackgroundColor(Color.argb(100,150, 200, 0))
            }
            in 60..99 -> {
                barthelResult.setText("Dependència greu")
                cardBarthel.setCardBackgroundColor(Color.argb(100,255, 200, 0))
            }
            else -> {
                barthelResult.setText("Dependència total")
                cardBarthel.setCardBackgroundColor(Color.argb(100,255, 0, 0))
            }
        }

        images.add(necroticBitmap)
        images.add(grainBitmap)
        images.add(infectedBitmap)


    }

}






