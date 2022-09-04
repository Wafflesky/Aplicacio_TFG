package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private val NUM_PAGES = 3

class ConfirmActivity: AppCompatActivity() {

    private lateinit var confirmButton: Button
    private lateinit var cancelButton: Button

    private lateinit var editTextNHC: EditText

    private var enableConfirm: Boolean = false
    private lateinit var necroticBitmap: Bitmap
    private lateinit var grainBitmap: Bitmap
    private lateinit var infectedBitmap: Bitmap

    private lateinit var tabLayout: TabLayout

    private var nhcEntries: Int = 0

    private val mDatabase = Firebase.database("https://alex-tfg-default-rtdb.europe-west1.firebasedatabase.app")


    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_confirm, null)


        confirmButton = findViewById(R.id.confirm_button)

        tabLayout = findViewById(R.id.Tab)
        editTextNHC = findViewById(R.id.editText_nhc)

        necroticBitmap = bitmapSingleton.getSelectedNecroticBitmap()
        grainBitmap = bitmapSingleton.getSelectedGrainBitmap()
        infectedBitmap = bitmapSingleton.getSelectedInfectedBitmap()

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()

        necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap,
            originalWidth as Int, originalHeight as Int, false)
        grainBitmap = Bitmap.createScaledBitmap(grainBitmap, originalWidth, originalHeight, false)
        infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap, originalWidth, originalHeight, false)

        if (editTextNHC.text != null){

            enableConfirm = true
        }

        confirmButton.setOnClickListener {

            if(enableConfirm) {

                bitmapSingleton.storeNHC(editTextNHC.text.toString())
                val myRef = mDatabase.getReference()
                myRef.child("Patients").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (jobSnapshot in dataSnapshot.children) {

                            val dbNHC = jobSnapshot.child("nhc").getValue(String::class.java)
                            val patient = jobSnapshot.child("patient").getValue(Patient::class.java)

                            val nhc = editTextNHC.text.toString()
                            if (patient != null) {
                                if(dbNHC == nhc){
                                    nhcEntries += 1
                                    Log.i("papa","Aaaaaaa")
                                }

                                //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                                Log.i("firebase", "Got value ${patient}")
                            }

                        }
                        bitmapSingleton.storeNHCEntryCreation(nhcEntries)
                        if(nhcEntries > 0){
                            val intent = Intent(this@ConfirmActivity, AddExistingInfoActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            val intent = Intent(this@ConfirmActivity, AddNewInfoActivity::class.java)
                            startActivity(intent)
                        }
                        // go to next step from here e.g handlePosts(posts);
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.i(javaClass.name.toString(), ": " + databaseError.message)
                    }
                })

            }

        }

        var prefragment: Fragment? = null
        val fm = supportFragmentManager
        prefragment = NecroticFragment()
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.simpleFrameLayout, prefragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

        tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
// get the current selected tab's position and replace the fragment accordingly
                var fragment: Fragment? = null
                when (tab.position) {
                    0 -> fragment = NecroticFragment()
                    1 -> fragment = GrainFragment()
                    2 -> fragment = InfectedFragment()
                }
                val fm = supportFragmentManager
                val ft: FragmentTransaction = fm.beginTransaction()
                if (fragment != null) {
                    ft.replace(R.id.simpleFrameLayout, fragment)
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}