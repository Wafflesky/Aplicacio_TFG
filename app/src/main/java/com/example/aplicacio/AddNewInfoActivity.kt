package com.example.aplicacio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get

class AddNewInfoActivity: AppCompatActivity() {

    private lateinit var patient: TextView
    private lateinit var age: Number
    private lateinit var ageInput: EditText
    private lateinit var gender: Spinner
    private lateinit var selectedGender: String
    private lateinit var region: Spinner
    private lateinit var confirmButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient_info)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_add_patient_info, null)

        patient = findViewById(R.id.patient3)
        ageInput = findViewById(R.id.editTextNumber)
        gender = findViewById(R.id.spinnerAge)
        val stringGenders = resources.getStringArray(R.array.Genders)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringGenders
        )
        gender.adapter = adapter
        gender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                selectedGender = stringGenders[position].toString()
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        region = findViewById(R.id.spinnerBruise)
        confirmButton = findViewById(R.id.confirm)

        if(ageInput.text != null){
            confirmButton.setOnClickListener{
                age = ageInput.text.toString().toIntOrNull()!!
                bitmapSingleton.storeAge(age)
                bitmapSingleton.storeGender(selectedGender)
                val intent = Intent(this, ResultActivity::class.java)
                this.startActivity(intent)

            }
        }

    }
}