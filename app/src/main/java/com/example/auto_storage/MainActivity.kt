package com.example.auto_storage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auto_storage.settings.SettingsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

private interface ASort {
    fun sort(allautolist: ArrayList<Autos>)
}

private enum class AutosSort : ASort {
    carbrand {
        override fun sort(allautolist: ArrayList<Autos>) = allautolist.sortBy { it.carbrand }
    },
    caryear {
        override fun sort(allautolist: ArrayList<Autos>) = allautolist.sortBy { it.caryear }
    },
    carprice {
        override fun sort(allautolist: ArrayList<Autos>) = allautolist.sortBy { it.carprice }
    }
}
private var sortField = AutosSort.carbrand

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var dbHelper: DBHelper
    }

    private fun viewAutos(){
        var allautolist: ArrayList<Autos> = dbHelper.getAllAutos(this)
        sortField.sort(allautolist)
        var adapter = AutoAdapter(this,allautolist)
        val rv:RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter = adapter

    }

    override fun onResume() {

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val sort_answer: String = prefs.getString("pref_car","1").toString()

        Toast.makeText(applicationContext,"sort_answer: " + sort_answer,Toast.LENGTH_LONG).show()

        if (sort_answer.contains("Car Brand")) {
            sortField = AutosSort.carbrand
            viewAutos()
            Toast.makeText(applicationContext,"Sort by Car Brand",Toast.LENGTH_LONG).show()
        }
        if (sort_answer.contains("Year")) {
            sortField = AutosSort.caryear
            viewAutos()
            Toast.makeText(applicationContext,"Sort by Year",Toast.LENGTH_LONG).show()
        }

        if (sort_answer.contains("Price")) {
            sortField = AutosSort.carprice
            viewAutos()
            Toast.makeText(applicationContext,"Sort by Price",Toast.LENGTH_LONG).show()
        }

        if (sort_answer.contains("1")) {
            viewAutos()
            Toast.makeText(applicationContext,"Sort by default value",Toast.LENGTH_LONG).show()
        }

        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this,null,null,1)
        viewAutos()



        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
            startActivity(intent)
        })

        val fabSort : FloatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        fabSort.setOnClickListener(View.OnClickListener {
            val intent = Intent (this,SettingsActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_carbrand -> {
                sortField = AutosSort.carbrand
                viewAutos()
                return true
            }
            R.id.action_year -> {
                sortField = AutosSort.caryear
                viewAutos()
                return true
            }
            R.id.action_price -> {
                sortField = AutosSort.carprice
                viewAutos()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}





