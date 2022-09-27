package com.example.aplicacio.ViewModel

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.aplicacio.R

class ImageFragment(val bitmap: Any?) : Fragment() {

    private lateinit var mBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_image, container,
            false
        )
        mBitmap = bitmap as Bitmap
        val imageView: ImageView = rootView.findViewById<View>(R.id.Infected_Tab) as ImageView
        
        imageView.setImageBitmap(mBitmap)

        return rootView
    }

}