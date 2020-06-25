package com.example.auto_storage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewAutos()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
        dbHelper = DBHelper(this,null,null,1)
        viewAutos()



        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
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





