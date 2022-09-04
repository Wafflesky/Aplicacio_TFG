package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*


class ResultActivity : AppCompatActivity() {

    private lateinit var confirmButton: Button

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

    private lateinit var gridView: GridView

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_result, null)


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

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()

        necroticBitmap = bitmapSingleton.getNecroticBitmap()
        necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap,
            originalWidth as Int, originalHeight as Int, false)

        grainBitmap = bitmapSingleton.getGrainBitmap()
        grainBitmap = Bitmap.createScaledBitmap(grainBitmap, originalWidth, originalHeight, false)

        infectedBitmap = bitmapSingleton.getInfectedBitmap()
        infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap, originalWidth, originalHeight, false)

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
            0 -> eminaResult.setText("Sense risc")
            in 1..3 -> eminaResult.setText("Risc lleu")
            in 4..7 -> eminaResult.setText("Risc mig")
            else -> eminaResult.setText("Risc alt")
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
            in 0..19 -> barthelResult.setText("Dependència total")
            in 20..39 -> barthelResult.setText("Dependència greu")
            in 40..59 -> barthelResult.setText("Dependència moderada")
            in 60..99 -> barthelResult.setText("Dependència lleu")
            else -> barthelResult.setText("Independència")
        }

        val images = mutableListOf<Bitmap>()
        images.add(necroticBitmap)
        images.add(grainBitmap)
        images.add(infectedBitmap)


        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridAdapter(null,images, context = this,false)

        // on below line we are setting adapter to our grid view.
        gridView.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
        }


        confirmButton.setOnClickListener{

            val database = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")
            val storage = Firebase.storage
            val myRef = database.getReference().child("Patients").push()

            var cont = false

            val storageRef = storage.reference

            val necroticBaos = ByteArrayOutputStream()
            val infectedBaos = ByteArrayOutputStream()
            val grainBaos = ByteArrayOutputStream()

            val nhcEntry = bitmapSingleton.getNHCEntries()

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
                                        infectedImage = infectedUUID
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
                                                    finish()
                                                }
                                            }

                                        })

                                    val intent = Intent(this, HomeActivity::class.java)
                                    this.startActivity(intent)

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}






