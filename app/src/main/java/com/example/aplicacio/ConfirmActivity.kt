package com.example.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


private val NUM_PAGES = 3

class ConfirmActivity: AppCompatActivity() {

    private lateinit var confirmButton: Button
    private lateinit var cancelButton: Button
    private lateinit var necrotic: Button
    private lateinit var grain: Button
    private lateinit var infected: Button

    private lateinit var image: ImageView

    private lateinit var imageBitmap: Bitmap
    private lateinit var necroticBitmap: Bitmap
    private lateinit var grainBitmap: Bitmap
    private lateinit var infectedBitmap: Bitmap

    private lateinit var tabLayout: TabLayout


    private lateinit var mPager: ViewPager

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val inflater =
            LayoutInflater.from(this) // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val viewMyLayout: View = inflater.inflate(R.layout.activity_confirm, null)


        confirmButton = findViewById(R.id.confirm_button)
        cancelButton = findViewById(R.id.existing)
        //necrotic = findViewById(R.id.Necrotic)
        //grain = findViewById(R.id.Grain)
        //infected = findViewById(R.id.Infected)
        tabLayout = findViewById(R.id.Tab)
        //image = findViewById(R.id.necroticImage)

        necroticBitmap = bitmapSingleton.getSelectedNecroticBitmap()
        grainBitmap = bitmapSingleton.getSelectedGrainBitmap()
        infectedBitmap = bitmapSingleton.getSelectedInfectedBitmap()

        confirmButton.setOnClickListener {

            val intent = Intent(this, AddNewInfoActivity::class.java)
            this.startActivity(intent)

        }
        cancelButton.setOnClickListener {


            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("existing", true)
            this.startActivity(intent)

        }

        var prefragment: Fragment? = null
        val fm = supportFragmentManager
        prefragment = NecroticFragment()
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.simpleFrameLayout, prefragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

        /*necrotic.setOnClickListener{
            val intent = Intent(this, CanvasActivity::class.java)
            this.startActivity(intent)

        }
        grain.setOnClickListener{
            val intent = Intent(this, CanvasActivity::class.java)
            this.startActivity(intent)

        }
        infected.setOnClickListener{
            val intent = Intent(this, CanvasActivity::class.java)
            this.startActivity(intent)
        }*/

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