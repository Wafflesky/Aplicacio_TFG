package com.example.aplicacio.ViewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.aplicacio.Model.Patient
import com.example.aplicacio.R
import com.example.aplicacio.Model.bitmapSingleton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Classe on trobem les seleccions separades en tres imatges diferents. Aqui l'usuari pot comprovar
 * les seleccions fetes i accedir a la pantalla d'omplir la informació del pacient mitjançant el
 * número d'historial clinic d'aquest
 */
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


    /**
     * Funció que es crida en la creació de la activitat. Aqui carreguem els bitmaps que s'han
     * aconseguit en la pantalla del canvas i s'afegeixen en el tabLayout
     */
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



        //Aqui es comprova si l'usuari ha introduït el numero d'historal Clinic i es comprova si
        // aquest existeix dins la base de dades. Depenent de si existeix o no es porta a l'usuari
        // a la pantalla d'afegir informació d'un pacient nou o el d'un pacient existent.
        confirmButton.setOnClickListener {

            if(editTextNHC.text.toString().equals("")){
                editTextNHC.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
            }
            else{
                editTextNHC.background.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
            }

            if(!editTextNHC.text.toString().equals("")) {

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
                                }

                                //val item = Json.decodeFromJsonElement<Patient>(patient.toJsonObject())
                                Log.i("firebase", "Got value ${patient}")
                            }

                        }
                        bitmapSingleton.storeNHCEntryCreation(nhcEntries)
                        if(nhcEntries > 0){
                            nhcEntries = 0
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
        prefragment = ImageFragment(necroticBitmap)
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.simpleFrameLayout, prefragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

        tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
// get the current selected tab's position and replace the fragment accordingly
                var fragment: Fragment? = null
                when (tab.position) {
                    0 -> fragment = ImageFragment(necroticBitmap)
                    1 -> fragment = ImageFragment(grainBitmap)
                    2 -> fragment = ImageFragment(infectedBitmap)
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