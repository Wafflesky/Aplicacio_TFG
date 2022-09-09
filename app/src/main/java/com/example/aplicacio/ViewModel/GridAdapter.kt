package com.example.aplicacio.ViewModel


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.aplicacio.R

// on below line we are creating an
// adapter class for our grid view.
internal class GridAdapter(
    // on below line we are creating two
    // variables for course list and context
    private val images: MutableList<String>?,
    private val imageBitmap: MutableList<Bitmap>?,
    private val context: Context,
    private val downloadImage: Boolean
) :
    BaseAdapter() {
    // in base adapter class we are creating variables
    // for layout inflater, course image view and course text view.
    private var layoutInflater: LayoutInflater? = null
    private lateinit var image: ImageView
    override fun getCount(): Int {
        if(images != null){
            return images.size
        }

        if(imageBitmap != null){
            return imageBitmap.size
        }

        return 0
    }

    // below method is use to return the count of course list

    // below function is use to return the item of grid view.
    override fun getItem(position: Int): Any? {
        return null
    }

    // below function is use to return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // in below function we are getting individual item of grid view.
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        convertView = layoutInflater!!.inflate(R.layout.fragment_images_grid, null)
        image = convertView!!.findViewById(R.id.idIVCourse)
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if(downloadImage) {
            Glide
                .with(context)
                .load(images?.get(position))
                .into(image)
        }
        else {
            image.setImageBitmap(imageBitmap?.get(position))
        }
        return convertView
    }
}


