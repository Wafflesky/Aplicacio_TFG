package com.example.aplicacio.ViewModel

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.aplicacio.R
import com.example.aplicacio.Model.bitmapSingleton


class GrainFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = inflater.inflate(
            R.layout.fragment_grain, container,
            false
        )

        val imageView: ImageView = rootView.findViewById<View>(R.id.Grain_Tab) as ImageView
        var grainBitmap: Bitmap = bitmapSingleton.getSelectedGrainBitmap()

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()
        grainBitmap = Bitmap.createScaledBitmap(grainBitmap,
            originalWidth as Int, originalHeight as Int, false)

        imageView.setImageBitmap(grainBitmap)

        return rootView

    }

}