package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacio.Model.Barthel
import com.example.aplicacio.Model.Emina
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.R
import com.example.aplicacio.Model.bitmapSingleton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Classe on es troba el formulari que l'usuari ha d'omplir en cas que el pacient ja existeixi a
 * la base de dades.
 */
class AddExistingInfoActivity: AppCompatActivity() {

    private lateinit var patient: TextView
    private lateinit var dob: TextView

    private lateinit var confirmButton: Button
    private lateinit var NHC: TextView
    private lateinit var name: TextView
    private lateinit var patologies: TextInputEditText

    private var blockIntent: Boolean = true


    private lateinit var region: EditText
    private lateinit var treatment: EditText
    private lateinit var bruiseDescription: TextInputEditText

    private lateinit var statusSpinner: Spinner
    private lateinit var mobilitySpinner: Spinner
    private lateinit var incontinencySpinner: Spinner
    private lateinit var nutritionSpinner: Spinner
    private lateinit var activitySpinner: Spinner

    private lateinit var selectedStatus: String
    private lateinit var selectedMobility: String
    private lateinit var selectedIncontinency: String
    private lateinit var selectedNutrition: String
    private lateinit var selectedActivity: String
    private var eminaResult = intArrayOf(0,0,0,0,0)

    private lateinit var eatSpinner: Spinner
    private lateinit var bathSpinner: Spinner
    private lateinit var dressSpinner: Spinner
    private lateinit var tidingSpinner: Spinner
    private lateinit var depositionSpinner: Spinner
    private lateinit var bladderSpinner: Spinner
    private lateinit var bathroomSpinner: Spinner
    private lateinit var moveSpinner: Spinner
    private lateinit var deambulateSpinner: Spinner
    private lateinit var stairSpinner: Spinner

    private lateinit var selectedEat: String
    private lateinit var selectedBath: String
    private lateinit var selectedDress: String
    private lateinit var selectedTiding: String
    private lateinit var selectedDeposition: String
    private lateinit var selectedBladder: String
    private lateinit var selectedBathroom: String
    private lateinit var selectedMove: String
    private lateinit var selectedDeambulate: String
    private lateinit var selectedStair: String

    private var barthelResult = intArrayOf(0,0,0,0,0,0,0,0,0,0)

    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")


    @SuppressLint("ResourceAsColor")
    /**
     * Funció que es crida en el moment que es crea la classe, aquí es carrega la vista i
     * connectem els elements amb el codi. Es fa la connexió amb la base de dades per a aconseguir
     * la informació existent de l'usuari com la data de naixement o el seu nom. Un cop es clica el
     * boto de confirmació s'activa el onClickListener per a passar a la següent pantalla.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addinfo)


        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_add_patient_info, null)

        setupView()

        setupAdapters()

        confirmButton = findViewById(R.id.confirm)

        val requestedNHC = bitmapSingleton.getNHC()

        //Connexio amb la base de dades, cercant dins de l'atribut Pacients
        val myRef = mDatabase.getReference()
        myRef.child("Patients").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    val pacient = Patient()
                    //pacient.DoB = jobSnapshot.child("DoB").getValue().toString()
                    val nhc = jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    val patient = jobSnapshot.child("patient").getValue(Patient::class.java)

                    if (nhc.equals(requestedNHC)) {
                        val birthdateText: String
                        val nhcText: String
                        val nameText: String
                        if (patient != null) {
                            birthdateText = patient.DoB
                            nhcText = requestedNHC
                            nameText = patient.name
                            dob.setText(birthdateText)
                            NHC.setText(nhcText)
                            name.setText(nameText)
                            bitmapSingleton.storeNHC(nhc)
                            bitmapSingleton.storeDoB(patient.DoB)
                            bitmapSingleton.storeName(patient.name)

                        }

                    }

                }

                // go to next step from here e.g handlePosts(posts);
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(javaClass.name.toString(), ": " + databaseError.message)
            }
        })

            blockIntent = false
            confirmButton.setOnClickListener{

                //Control dels colors/informació en cas d'informació no introduïda
                if(region.text.toString().equals("")){
                    region.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                }
                else{
                    region.background.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
                }

                if(treatment.text.toString().equals("")){
                    treatment.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                }
                else{
                    treatment.background.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
                }

                if(patologies.text.toString().equals(""))
                {
                    patologies.setText("Cap informació afegida")

                }
                if(bruiseDescription.text.toString().equals("")){
                    bruiseDescription.setText("Cap informació afegida")
                }

                //Comprovació de la informació no introduïda
                if(!region.text.toString().equals("") && !treatment.text.toString().equals(""))
                {

                    bitmapSingleton.storePatologies(patologies.text.toString())
                    bitmapSingleton.storeRegion(region.text.toString())
                    bitmapSingleton.storeTreatment(treatment.text.toString())
                    bitmapSingleton.storeDesc(bruiseDescription.text.toString())

                    val resultEmina = eminaResult.sum()
                    var emina = Emina(activity = selectedActivity,
                        incontinency = selectedIncontinency,
                        mentalStatus = selectedStatus,
                        mobility = selectedMobility,
                        nutrition = selectedNutrition,
                        eminaResult = resultEmina.toString())
                    bitmapSingleton.storeEmina(emina, eminaResult =  resultEmina)

                    var resultBarthel = barthelResult.sum()
                    var barthel = Barthel(bath = selectedBath,
                        bathroom = selectedBathroom,
                        bladder = selectedBladder ,
                        deambulate = selectedDeambulate,
                        deposition = selectedDeposition,
                        dress = selectedDress,
                        eat = selectedEat,
                        move = selectedMove,
                        stair = selectedStair,
                        tiding = selectedTiding,
                        barthelResult = resultBarthel.toString())

                    bitmapSingleton.storeBarthel(barthel,resultBarthel)

                    val intent = Intent(this, ResultActivity::class.java)
                    this.startActivity(intent)

                }


            }
        }

    /**
     * Funció per a fer la connexió entre els elements del codi amb els seus respectius elements
     * en la vista mitjançant les seves ID.
      */
    fun setupView(){

        dob = findViewById(R.id.dobTextView)
        NHC = findViewById(R.id.NHCTextView)
        name = findViewById(R.id.NameTextView)
        patologies = findViewById(R.id.patologies)

        region = findViewById(R.id.editTextTextPersonName2)
        treatment = findViewById(R.id.editTextTextPersonName3)
        bruiseDescription = findViewById(R.id.bruiseDesc)

        statusSpinner = findViewById(R.id.spinnerEstat)
        mobilitySpinner = findViewById(R.id.spinnerMovilitat)
        incontinencySpinner = findViewById(R.id.spinnerIncontinencia)
        nutritionSpinner = findViewById(R.id.spinnerNutricio)
        activitySpinner = findViewById(R.id.spinnerActivitat)

        eatSpinner = findViewById(R.id.spinnerEstat3)
        bathSpinner = findViewById(R.id.spinnerEstat2)
        dressSpinner = findViewById(R.id.spinnerEstat4)
        tidingSpinner = findViewById(R.id.spinnerEstat5)
        depositionSpinner = findViewById(R.id.spinnerEstat6)
        bladderSpinner = findViewById(R.id.spinnerEstat7)
        bathroomSpinner = findViewById(R.id.spinnerEstat8)
        moveSpinner = findViewById(R.id.spinnerEstat9)
        deambulateSpinner = findViewById(R.id.spinnerEstat10)
        stairSpinner = findViewById(R.id.spinnerEstat11)

        patient = findViewById(R.id.patient3)

    }

    /**
     * Funció per a carregar la informació que tenen els Spinners. El onItemSelectedListener és el
     * que utilitzarem per a saber si la informació dels spinners s'ha canviat
     */
    fun setupAdapters(){

        //Carregem la informació guardada en el arxiu res/values/strings
        val stringStatus = resources.getStringArray(R.array.Estat_mental)
        val stringMobility = resources.getStringArray(R.array.Movilitat)
        val stringIncontinent = resources.getStringArray(R.array.Incontinencia)
        val stringNutrition = resources.getStringArray(R.array.Nutrició)
        val stringActivity = resources.getStringArray(R.array.Activitat)

        val stringEat = resources.getStringArray(R.array.Barthel_3_option)
        val stringBath = resources.getStringArray(R.array.Barthel_2_option)
        val stringDress = resources.getStringArray(R.array.Barthel_3_option)
        val stringTiding = resources.getStringArray(R.array.Barthel_2_option)
        val stringDeposition = resources.getStringArray(R.array.Barthel_3_option_2)
        val stringMiccio = resources.getStringArray(R.array.Barthel_3_option_2)
        val stringBathroom = resources.getStringArray(R.array.Barthel_3_option)
        val stringMove = resources.getStringArray(R.array.Barthel_4_option_1)
        val stringDeambulate = resources.getStringArray(R.array.Barthel_4_option_2)
        val stringStair = resources.getStringArray(R.array.Barthel_3_option)

        //afegim la informació carregada en un adapter
        val statusAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringStatus
        )
        val mobilityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringMobility
        )
        val incontinentAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringIncontinent
        )
        val nutritionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringNutrition
        )
        val activityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringActivity
        )

        val eatAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringEat
        )
        val bathAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringBath
        )
        val dressAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringDress
        )
        val tidingAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringTiding
        )
        val depositionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringDeposition
        )
        val bladderAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringMiccio
        )
        val bathroomAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringBathroom
        )
        val moveAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringMove
        )
        val deambulateAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringDeambulate
        )
        val stairAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringStair
        )

        //Connectem el adapter amb el Spinner
        statusSpinner.adapter = statusAdapter
        mobilitySpinner.adapter = mobilityAdapter
        incontinencySpinner.adapter = incontinentAdapter
        nutritionSpinner.adapter = nutritionAdapter
        activitySpinner.adapter = activityAdapter

        eatSpinner.adapter = eatAdapter
        bathSpinner.adapter = bathAdapter
        dressSpinner.adapter = dressAdapter
        tidingSpinner.adapter = tidingAdapter
        depositionSpinner.adapter = depositionAdapter
        bladderSpinner.adapter = bladderAdapter
        bathroomSpinner.adapter = bathroomAdapter
        moveSpinner.adapter = moveAdapter
        deambulateSpinner.adapter = deambulateAdapter
        stairSpinner.adapter = stairAdapter

        //Creem el listener per a canviar el valor del spinner en cas que l'usuari el canviï
        statusSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedStatus = stringStatus[position].toString()

                eminaResult[0] = position

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        mobilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedMobility = stringMobility[position].toString()

                eminaResult[1] = position
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        incontinencySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedIncontinency = stringIncontinent[position].toString()

                eminaResult[2] = position
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        nutritionSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedNutrition = stringNutrition[position].toString()

                eminaResult[3] = position
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        activitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedActivity = stringActivity[position].toString()

                eminaResult[4] = position
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        eatSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedEat = stringEat[position].toString()

                when (position) {
                    0 -> barthelResult[0] = position
                    1 -> barthelResult[0] = 5
                    2 -> barthelResult[0] = 10

                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        bathSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedBath = stringBath[position].toString()

                when (position) {
                    0 -> barthelResult[1] = position
                    1 -> barthelResult[1] = 5
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        dressSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedDress = stringDress[position].toString()

                when (position) {
                    0 -> barthelResult[2] = position
                    1 -> barthelResult[2] = 5
                    2 -> barthelResult[2] = 10
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        tidingSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedTiding = stringTiding[position].toString()

                when (position) {
                    0 -> barthelResult[3] = position
                    1 -> barthelResult[3] = 5
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        depositionSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedDeposition = stringDeposition[position].toString()

                when (position) {
                    0 -> barthelResult[4] = position
                    1 -> barthelResult[4] = 5
                    2 -> barthelResult[4] = 10
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        bladderSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedBladder = stringMiccio[position].toString()

                when (position) {
                    0 -> barthelResult[5] = position
                    1 -> barthelResult[5] = 5
                    2 -> barthelResult[5] = 10
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        bathroomSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedBathroom = stringBathroom[position].toString()

                when (position) {
                    0 -> barthelResult[6] = position
                    1 -> barthelResult[6] = 5
                    2 -> barthelResult[6] = 10
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        moveSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedMove = stringMove[position].toString()

                when (position) {
                    0 -> barthelResult[7] = position
                    1 -> barthelResult[7] = 5
                    2 -> barthelResult[7] = 10
                    3 -> barthelResult[7] = 15
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        deambulateSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedDeambulate = stringDeambulate[position].toString()

                when (position) {
                    0 -> barthelResult[8] = position
                    1 -> barthelResult[8] = 5
                    2 -> barthelResult[8] = 10
                    3 -> barthelResult[8] = 15
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        stairSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedStair = stringStair[position].toString()

                when (position) {
                    0 -> barthelResult[9] = position
                    1 -> barthelResult[9] = 5
                    2 -> barthelResult[9] = 10
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }
}
