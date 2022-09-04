package com.example.aplicacio

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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.chromium.base.Promise
import org.json.JSONObject


class UserEntriesActivity: AppCompatActivity() {

    lateinit var list: ListView
    lateinit var searchView: SearchView
    lateinit var adapter: ArrayAdapter<*>
    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")

    private var patients: ArrayList<Patient> = ArrayList()

    private val nameList = mutableListOf<String>()
    //private var nhc:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        list = findViewById(R.id.list)

        searchView = findViewById(R.id.searchView)

        var selectedNHC = intent.getStringExtra("nhc").toString()
        var selectedEntryNumber = 0

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
                            var entryNumber =  patient.entryNumber + 1
                            nameList.add(entryNumber.toString())
                        }
                    }
                }
                // go to next step from here e.g handlePosts(posts);
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(javaClass.name.toString(), ": " + databaseError.message)
            }
        })



        list.adapter = UserListAdapter(this,R.layout.fragment_user_list,nameList)
        list.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val clicked = itemAtPos
            //TODO: Aixo ens agafa per NHC, perfecte per a cridar a base de dades
            Toast.makeText(this,
                "Click on item $clicked"
                ,Toast.LENGTH_LONG ).show()
            // Toast.makeText(this, "You Clicked:"+" "+position,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserDataActivity::class.java)
            bitmapSingleton.storeNHC(selectedNHC)
            var entry = clicked.toString().toInt()
            bitmapSingleton.storeNHCEntry(entry)
            //intent.putExtra("nhc", selectedNHC)
            //intent.putExtra("entryNumber",clicked.toString())
            this.startActivity(intent)
        }


        adapter =  UserListAdapter(this,R.layout.fragment_user_list,nameList)
        list.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String): Boolean {

                if (nameList.contains(query)) {
                    adapter.filter.filter(query)

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
    }

    /**
     * https://github.com/Kotlin/kotlinx.serialization/issues/746#issuecomment-737000705
     */

}