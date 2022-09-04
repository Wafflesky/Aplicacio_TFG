package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase


class AddNewInfoActivity: AppCompatActivity() {

    private lateinit var patient: TextView
    private lateinit var age: Number
    private lateinit var dob: EditText
    private lateinit var gender: Spinner

    private lateinit var confirmButton: Button
    private lateinit var NHC: EditText
    private lateinit var name: EditText
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
    private var emina = intArrayOf(0,0,0,0,0)

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

    private var barthel = intArrayOf(0,0,0,0,0,0,0,0,0,0)


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient_info)


        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_add_patient_info, null)

        val requestedNHC = bitmapSingleton.getNHC()

        dob = findViewById(R.id.editTextDate)
        NHC = findViewById(R.id.editTextNumber)
        name = findViewById(R.id.editTextTextPersonName)
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

        setupAdapters()

        confirmButton = findViewById(R.id.confirm)

        NHC.setText(requestedNHC)

        if(NHC.text != null && dob.text != null && name.text != null){
            blockIntent = false
            confirmButton.setOnClickListener{
                if(NHC.text.toString().equals("")){
                    NHC.setBackgroundColor(R.color.Button_Red)
                    blockIntent = true
                }
                if(dob.text.toString().equals("")){
                    dob.setBackgroundColor(R.color.Button_Red)
                    blockIntent = true
                }
                if(name.text.toString().equals("")){
                    name.setBackgroundColor(R.color.Button_Red)
                    blockIntent = true
                }
                if(!blockIntent){

                    bitmapSingleton.storeNHC(NHC.text.toString())
                    bitmapSingleton.storeDoB(dob.text.toString())
                    bitmapSingleton.storeName(name.text.toString())
                    bitmapSingleton.storePatologies(patologies.text.toString())
                    bitmapSingleton.storeRegion(region.text.toString())
                    bitmapSingleton.storeTreatment(treatment.text.toString())
                    bitmapSingleton.storeDesc(bruiseDescription.text.toString())

                    bitmapSingleton.storeMental(selectedStatus)
                    bitmapSingleton.storeMobility(selectedMobility)
                    bitmapSingleton.storeIncontinceny(selectedIncontinency)
                    bitmapSingleton.storeNutrition(selectedNutrition)
                    bitmapSingleton.storeActivity(selectedActivity)

                    val resultEmina = emina.sum()
                    bitmapSingleton.storeEmina(resultEmina)

                    bitmapSingleton.storeEat(selectedEat)
                    bitmapSingleton.storeBath(selectedBath)
                    bitmapSingleton.storeDress(selectedDress)
                    bitmapSingleton.storeTiding(selectedTiding)
                    bitmapSingleton.storeDeposition(selectedDeposition)
                    bitmapSingleton.storeBladder(selectedBladder)
                    bitmapSingleton.storeBathroom(selectedBathroom)
                    bitmapSingleton.storeMove(selectedMove)
                    bitmapSingleton.storeDeambulate(selectedDeambulate)
                    bitmapSingleton.storeStair(selectedStair)

                    val resultBarthel = barthel.sum()
                    bitmapSingleton.storeBarthel(resultBarthel)

                    val intent = Intent(this, ResultActivity::class.java)
                    this.startActivity(intent)
                }

            }
        }

    }

    fun setupAdapters(){
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

        statusSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedStatus = stringStatus[position].toString()

                emina[0] = position

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

                emina[1] = position
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

                emina[2] = position
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

                emina[3] = position
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

                emina[4] = position
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
                    0 -> barthel[0] = position
                    1 -> barthel[0] = 5
                    2 -> barthel[0] = 10

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
                    0 -> barthel[1] = position
                    1 -> barthel[1] = 5
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
                    0 -> barthel[2] = position
                    1 -> barthel[2] = 5
                    2 -> barthel[2] = 10
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
                    0 -> barthel[3] = position
                    1 -> barthel[3] = 5
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
                    0 -> barthel[4] = position
                    1 -> barthel[4] = 5
                    2 -> barthel[4] = 10
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
                    0 -> barthel[5] = position
                    1 -> barthel[5] = 5
                    2 -> barthel[5] = 10
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
                    0 -> barthel[6] = position
                    1 -> barthel[6] = 5
                    2 -> barthel[6] = 10
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
                    0 -> barthel[7] = position
                    1 -> barthel[7] = 5
                    2 -> barthel[7] = 10
                    3 -> barthel[7] = 15
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
                    0 -> barthel[8] = position
                    1 -> barthel[8] = 5
                    2 -> barthel[8] = 10
                    3 -> barthel[8] = 15
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
                    0 -> barthel[9] = position
                    1 -> barthel[9] = 5
                    2 -> barthel[9] = 10
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }
}