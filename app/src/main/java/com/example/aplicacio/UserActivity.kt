package com.example.aplicacio


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class UserActivity: AppCompatActivity() {

    lateinit var list: ListView
    lateinit var searchView: SearchView
    lateinit var adapter: ArrayAdapter<*>
    var existingUser: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        existingUser = intent.getBooleanExtra("existing", existingUser)
        list = findViewById(R.id.list)
        val itemList = mutableListOf<Model>()
        val reducedList = mutableListOf<Model>()
        val nameList = mutableListOf<String>()
        searchView = findViewById(R.id.searchView)


        //TODO: Per a tenir el item per a passar ens guardem el nhc primer, que hauria de ser unic
        itemList.add(Model("23544",   "23544",   "29/2/1956" ))
        itemList.add(Model("Jane Doe",   "122345",  "2t/2/1956"  ))
        itemList.add(Model("Joe Mama", "236665", "20/4/1969" ))
        itemList.add(Model("John Doe",   "23544",   "29/2/1956" ))
        itemList.add(Model("Jane Doe",   "122345",  "2t/2/1956"  ))
        itemList.add(Model("Joe Mama", "236665", "20/4/1969" ))
        itemList.add(Model("John Doe",   "23544",   "29/2/1956" ))
        itemList.add(Model("Jane Doe",   "122345",  "2t/2/1956"  ))
        itemList.add(Model("Joe Mama", "236665", "20/4/1969" ))

        for (i in itemList){
            nameList.add(i.name)
        }

        list.adapter = UserListAdapter(this,R.layout.fragment_user_list,itemList,null)
        list.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            //Toast.makeText(this,
                //"Click on item at $itemAtPos its item id $itemIdAtPos",
                //Toast.LENGTH_LONG ).show()
            val clicked = itemAtPos as Model
            //TODO: Aixo ens agafa per NHC, perfecte per a cridar a base de dades
            clicked.nhc
            Toast.makeText(this,
                "Click on item ${clicked.nhc}"
                ,Toast.LENGTH_LONG ).show()
            // Toast.makeText(this, "You Clicked:"+" "+position,Toast.LENGTH_SHORT).show()
            if(!existingUser){
                val intent = Intent(this, UserDataActivity::class.java)
                this.startActivity(intent)
            }
            else{
                val intent = Intent(this,ResultActivity::class.java)

                this.startActivity(intent)
            }
        }

        adapter =  UserListAdapter(this,R.layout.fragment_user_list,itemList,nameList)
        list.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String): Boolean {

                if (nameList.contains(query)) {
                    adapter.filter.filter(query)

                } else {
                    Toast.makeText(this@UserActivity, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })


    }
}