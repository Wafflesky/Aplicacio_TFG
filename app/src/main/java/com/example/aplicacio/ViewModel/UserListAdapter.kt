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

/**
 * Classe que crea cada element que trobem en el llistat del UserActivity.
 * Aqui es prepara el requadre amb la informació de cada pacient
 */
class UserListAdapter(var mCtx: Context, var resource:Int, var items: MutableList<String>)
    :ArrayAdapter<String>( mCtx , resource , items as MutableList<String>){

    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")


    @SuppressLint("ViewHolder")
    /**
     * Aquesta funcio agafa el fragment_user_list i l'omple amb la informació de cada pacient.
     * Per a aconseguir aquesta informació es fa la connexió amb la base de dades i s'agafa els NHCS
     * unics. En el llistat mostrem un pacient amb el seu NHC tot i que les dades es puguin
     * repetir en Firebase
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(resource , null )
        val nhc : TextView = view.findViewById(R.id.nhc)
        val name : TextView = view.findViewById(R.id.namePlaceholder)

        val nhcText: String = items.get(position)

        val myRef = mDatabase.getReference()
        myRef.child("Patients").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    //pacient.DoB = jobSnapshot.child("DoB").getValue().toString()
                    val databaseNHC =
                        jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    if (databaseNHC.equals(nhcText)) {
                        val patient = jobSnapshot.child("patient").getValue(Patient::class.java)
                        //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())

                        if (patient != null) {
                            name.text= patient.name
                            nhc.text = nhcText

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