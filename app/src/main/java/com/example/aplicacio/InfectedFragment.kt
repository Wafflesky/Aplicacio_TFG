package com.example.aplicacio

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class InfectedFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_infected, container,
            false
        )

        val imageView: ImageView = rootView.findViewById<View>(R.id.Infected_Tab) as ImageView
        var infectedBitmap: Bitmap = bitmapSingleton.getSelectedInfectedBitmap()

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()
        infectedBitmap = Bitmap.createScaledBitmap(infectedBitmap,
            originalWidth as Int, originalHeight as Int, false)

        imageView.setImageBitmap(infectedBitmap)

        return rootView
    }

}