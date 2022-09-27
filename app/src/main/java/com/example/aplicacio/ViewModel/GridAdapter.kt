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


/**
 * Classe que s'utilitza per a carregar informació dins el element GridView i donar-li diferents funcionalitats
 */
internal class GridAdapter(
    // on below line we are creating two
    // variables for course list and context
    private val images: MutableList<String>?,
    private val imageBitmap: MutableList<Bitmap>?,
    private val context: Context,
    private val downloadImage: Boolean
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var image: ImageView

    /**
     * Funció que compta quantes variables hi ha dins la llista d'imatges
     */
    override fun getCount(): Int {
        if(images != null){
            return images.size
        }

        if(imageBitmap != null){
            return imageBitmap.size
        }

        return 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }


    override fun getItemId(position: Int): Long {
        return 0
    }


    @SuppressLint("ViewHolder")
    /**
     * Funció que s'utilitza per a introduir els valors a cada element del view. Aqui introduim
     * la nostra imatge mitjançant un link que ens proporciona firebase Storage
     */
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


