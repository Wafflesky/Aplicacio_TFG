package com.example.aplicacio

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*



import java.util.*
import java.util.logging.Level.parse

class UserListAdapter(var mCtx: Context, var resource:Int, var items: MutableList<String>)
    :ArrayAdapter<String>( mCtx , resource , items as MutableList<String>){




    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(resource , null )
        val nhc : TextView = view.findViewById(R.id.nhc)

        val nhcText: String? = items?.get(position)


        nhc.text = nhcText


        return view
    }




}