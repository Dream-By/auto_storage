package com.example.auto_storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auto_storage.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var dbHelper: DBHelper
    }

    private fun viewAutos(){
        val allautolist: ArrayList<Autos> = dbHelper.getAllAutos(this)
        val adapter = AutoAdapter(this,allautolist)
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
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
        dbHelper = DBHelper(this,null,null,1)
        viewAutos()



        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
            startActivity(intent)
        })

        val fabsort = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        fabsort.setOnClickListener(View.OnClickListener {

            val fragment = Fragment1()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        })

    }


}




