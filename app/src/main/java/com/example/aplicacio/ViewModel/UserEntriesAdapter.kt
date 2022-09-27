package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

/**
 * Classe que crea cada element que trobem en el llistat del UserEntriesActivity.
 * Aqui es prepara el requadre amb la informació de cada ferida
 */
class UserEntriesAdapter(var mCtx: Context, var resource:Int, var items: MutableList<String>, var NHC: String)
    :ArrayAdapter<String>( mCtx , resource , items as MutableList<String>){

    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")
    @SuppressLint("ViewHolder")
    /**
     * Aquesta funcio agafa el fragment_user_entries per a dibuixar el requadre que s'utilitza en
     * el llistat i l'omple amb la informació trobada del usuari seleccionat mitjançant el número
     * d'historial clinic
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(resource , null )
        val nhc : TextView = view.findViewById(R.id.nhc)
        val date: TextView = view.findViewById(R.id.datePlaceholder)

        val entryText: String = items.get(position)

        val myRef = mDatabase.getReference()
        myRef.child("Patients").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    //pacient.DoB = jobSnapshot.child("DoB").getValue().toString()
                    val databaseNHC =
                        jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    if (databaseNHC.equals(NHC)) {
                        val patient = jobSnapshot.child("patient").getValue(Patient::class.java)
                        //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                        Log.i("firebase2", "Got value ${nhc}")
                        Log.i("firebase2", "Patient ${patient}")
                        if (patient != null) {
                            val patientEntry = patient.entryNumber + 1
                            if (items.get(position).equals(patientEntry.toString())) {

                                nhc.text = patient.bruiseData.region
                                date.text = patient.dataEnregistrament

                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        return view
    }

}