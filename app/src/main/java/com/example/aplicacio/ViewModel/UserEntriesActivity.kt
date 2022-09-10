package com.example.aplicacio.ViewModel

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.Model.bitmapSingleton
import com.example.aplicacio.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class UserEntriesActivity: AppCompatActivity() {

    private lateinit var list: ListView
    private lateinit var pacientText: TextView
    private lateinit var searchView: SearchView
    private lateinit var adapter: ArrayAdapter<*>
    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")

    private var patients: ArrayList<Patient> = ArrayList()

    private val nameList = mutableListOf<String>()
    //private var nhc:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrieslist)

        list = findViewById(R.id.list)

        pacientText = findViewById(R.id.pacientName)

        searchView = findViewById(R.id.searchView)

        var selectedNHC = intent.getStringExtra("nhc").toString()

        val myRef = mDatabase.getReference()

        myRef.child("Patients").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    val pacient = Patient()
                    //pacient.DoB = jobSnapshot.child("DoB").getValue().toString()
                    val nhc = jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    val patient = jobSnapshot.child("patient").getValue(Patient::class.java)
                    //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                    Log.i("firebase2", "Got value ${nhc}")
                    Log.i("firebase2","Patient ${patient}")
                    if(selectedNHC.equals(nhc)){
                        if (patient != null) {
                            patients.add(patient)
                            val name = patient.name
                            val entryNumber =  patient.entryNumber + 1
                            pacientText.setText(name)
                            nameList.add(entryNumber.toString())
                        }
                    }
                }
                list.adapter = UserEntriesAdapter(this@UserEntriesActivity,
                    R.layout.fragment_user_entries,nameList,selectedNHC)
                list.setOnItemClickListener() { adapterView, view, position, id ->
                    val itemAtPos = adapterView.getItemAtPosition(position)
                    val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                    val clicked = itemAtPos
                    //TODO: Aixo ens agafa per NHC, perfecte per a cridar a base de dades
                    Toast.makeText(this@UserEntriesActivity,
                        "Click on item $clicked"
                        ,Toast.LENGTH_LONG ).show()
                    // Toast.makeText(this, "You Clicked:"+" "+position,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserEntriesActivity, UserDataActivity::class.java)
                    bitmapSingleton.storeNHC(selectedNHC)
                    var entry = clicked.toString().toInt()
                    bitmapSingleton.storeNHCEntry(entry)

                    startActivity(intent)
                }


                adapter =  UserEntriesAdapter(this@UserEntriesActivity,
                    R.layout.fragment_user_entries,nameList,selectedNHC)
                list.adapter = adapter
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onQueryTextSubmit(query: String): Boolean {

                        if (nameList.contains(query)) {
                            adapter.filter.filter(query)
                            list.adapter = adapter

                        } else {
                            Toast.makeText(this@UserEntriesActivity, "No Match found", Toast.LENGTH_LONG).show()
                        }
                        return false
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        adapter.filter.filter(newText)
                        return false
                    }
                })

                // go to next step from here e.g handlePosts(posts);
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(javaClass.name.toString(), ": " + databaseError.message)
            }
        })


    }

    /**
     * https://github.com/Kotlin/kotlinx.serialization/issues/746#issuecomment-737000705
     */

}