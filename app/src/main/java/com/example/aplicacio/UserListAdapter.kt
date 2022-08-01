package com.example.aplicacio

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
class UserListAdapter(var mCtx: Context, var resource:Int, var items: MutableList<Model>, var names: MutableList<String>?)
    :ArrayAdapter<Model>( mCtx , resource , items as MutableList<Model>){




    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(resource , null )
        val name :TextView = view.findViewById(R.id.title)
        val nhc : TextView = view.findViewById(R.id.nhc)
        val dob : TextView = view.findViewById(R.id.dob)

        val person : Model = items.get(position)

        name.text = person.name
        nhc.text = person.nhc
        dob.text = person.dob



        return view
    }

}