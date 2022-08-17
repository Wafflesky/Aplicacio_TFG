package com.example.aplicacio

import android.graphics.Bitmap
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView
import androidx.fragment.app.Fragment

class NecroticFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_necrotic, container,
            false
        )

        val imageView: ImageView = rootView.findViewById<View>(R.id.Necrotic_Tab) as ImageView
        var necroticBitmap: Bitmap = bitmapSingleton.getSelectedNecroticBitmap()

        val originalWidth = bitmapSingleton.getWidth()
        val originalHeight = bitmapSingleton.getHeight()
        necroticBitmap = Bitmap.createScaledBitmap(necroticBitmap,
            originalWidth as Int, originalHeight as Int, false)

        imageView.setImageBitmap(necroticBitmap)

        return rootView
    }

}