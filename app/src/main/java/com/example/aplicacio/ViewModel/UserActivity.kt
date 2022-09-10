package com.example.aplicacio.ViewModel


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserActivity: AppCompatActivity() {

    lateinit var list: ListView
    lateinit var searchView: SearchView
    lateinit var adapter: ArrayAdapter<*>
    var loadedInfo: Boolean = false
    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")

    private lateinit var histories : Historial
    private var patients: ArrayList<Patient> = ArrayList()

    private val nameList = mutableListOf<String>()
    private lateinit var nhc: String

    data class Historial(
        var patient: Patient,
        var nhc: String
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        list = findViewById(R.id.list)

        searchView = findViewById(R.id.searchView)

        val myRef = mDatabase.getReference()
        myRef.child("Patients").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {

                    val pacient = Patient()
                    //pacient.DoB = jobSnapshot.child("DoB").getValue().toString()
                    nhc = jobSnapshot.child("nhc").getValue(String::class.java).toString()
                    val patient = jobSnapshot.child("patient").getValue(Patient::class.java)
                    //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                    Log.i("firebase", "Got value ${nhc}")
                    Log.i("firebase","Patient ${patient}")
                    if (patient != null) {
                        patients.add(patient)
                        if(patient.entryNumber == 0){
                            nameList.add(nhc)
                        }
                    }
                }

                list.adapter = UserListAdapter(this@UserActivity,
                    R.layout.fragment_user_list,nameList)
                list.setOnItemClickListener() { adapterView, view, position, id ->
                    val itemAtPos = adapterView.getItemAtPosition(position)
                    val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                    val clicked = itemAtPos
                    //TODO: Aixo ens agafa per NHC, perfecte per a cridar a base de dades
                    Toast.makeText(this@UserActivity,
                        "Click on item $clicked"
                        ,Toast.LENGTH_LONG ).show()
                    // Toast.makeText(this, "You Clicked:"+" "+position,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserActivity, UserEntriesActivity::class.java)
                    intent.putExtra("nhc", clicked.toString())
                    startActivity(intent)

                }

                //Arreglar Query no funcionant correctament
                adapter =  UserListAdapter(this@UserActivity, R.layout.fragment_user_list,nameList)
                list.adapter = adapter
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    @RequiresApi(Build.VERSION_CODES.N)

                    //TODO: Qurery filter no canvia els valors de la llista correctament fa un pop,
                    // mante el primer valor i borra a sota
                    override fun onQueryTextSubmit(query: String): Boolean {

                        if (nameList.contains(query)) {
                            adapter.filter.filter(query)
                            list.adapter = adapter
                        } else {
                            Toast.makeText(this@UserActivity, "No Match found", Toast.LENGTH_LONG).show()
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