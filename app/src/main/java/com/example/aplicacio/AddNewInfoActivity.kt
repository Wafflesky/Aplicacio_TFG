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
    private lateinit var selectedGender: String
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




    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient_info)

        // Write a message to the database
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_add_patient_info, null)

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
                   //age = ageInput.text.toString().toInt()
                    //bitmapSingleton.storeAge(age)
                    //bitmapSingleton.storeGender(selectedGender)
                    val intent = Intent(this, ResultActivity::class.java)
                    this.startActivity(intent)
                }

            }
        }
        /*if(ageInput.text != null){
            confirmButton.setOnClickListener{
                if(ageInput.text.toString().equals("")){
                    ageInput.setBackgroundColor(R.color.Button_Red)
                }
                else {
                    age = ageInput.text.toString().toInt()
                    //bitmapSingleton.storeAge(age)
                    //bitmapSingleton.storeGender(selectedGender)
                    val intent = Intent(this, ResultActivity::class.java)
                    this.startActivity(intent)
                }

            }
        }*/

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
                selectedGender = stringStatus[position].toString()
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
                selectedGender = stringMobility[position].toString()
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
                selectedGender = stringIncontinent[position].toString()
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
                selectedGender = stringNutrition[position].toString()
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
                selectedGender = stringActivity[position].toString()
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
                selectedGender = stringEat[position].toString()
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
                selectedGender = stringBath[position].toString()
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
                selectedGender = stringDress[position].toString()
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
                selectedGender = stringTiding[position].toString()
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
                selectedGender = stringDeposition[position].toString()
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
                selectedGender = stringMiccio[position].toString()
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
                selectedGender = stringBathroom[position].toString()
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
                selectedGender = stringMove[position].toString()
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
                selectedGender = stringDeambulate[position].toString()
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
                selectedGender = stringStair[position].toString()
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }
}