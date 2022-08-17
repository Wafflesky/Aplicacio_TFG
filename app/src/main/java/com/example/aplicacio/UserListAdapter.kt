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

    // Code for Custom Filter.
    // https://stackoverflow.com/questions/42050789/android-adding-searchview-inside-a-custom-listview-with-custom-adapter
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val queryString = charSequence.toString().lowercase(Locale.getDefault())
                val filteredList: MutableList<Model> = ArrayList<Model>()
                var tmpItem: Model
                var tmpUsername: String
                var tmpStatus: String

                for (i in items) {
                    tmpItem = i
                    tmpUsername = tmpItem.name.lowercase()
                    tmpStatus = tmpItem.nhc.lowercase()
                    // The matching condition
                    if (tmpUsername.contains(queryString) || tmpStatus.contains(queryString)
                    ) {
                        filteredList.add(tmpItem)
                    }
                }
                val filterResults = FilterResults()
                filterResults.count = filteredList.size
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                clear()
                addAll(filterResults.values)
            }
        }
    }

    private fun addAll(values: Any?) {
        items = values as MutableList<Model>
    }



    fun updateBackupList(newList: Collection<Model>) {
        items.clear()
        items.addAll(newList)
    }



}