package com.example.auto_storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.sql.SQLException


private val LOG_TAG = "AutoLease"

class DBHelper(context: Context,name:String?,factory:SQLiteDatabase.CursorFactory?,version:Int) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "autoLease.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "AutoLease"
        val COLUMN_ID = "id"
        val COLUMN_Brand = "CarBrand"
        val COLUMN_Model = "CarModel"
        val COLUMN_Year = "Year"
        val COLUMN_Price = "Price"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val AUTO_TABLE_NAME = "CREATE TABLE  $TABLE_NAME  (" +
                "$COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_Brand  TEXT," +
                "$COLUMN_Model TEXT," +
                "$COLUMN_Year TEXT," +
                "$COLUMN_Price INTEGER);"
        try {
            if (db != null) {
                db.execSQL(AUTO_TABLE_NAME)
            }
        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            onCreate(db)
        }
        Log.d(LOG_TAG, "onUpgrade called")
    }

    fun getAllAutos(mAtx: Context): ArrayList<Autos> {

        val qry = "SELECT * From $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val autosList = ArrayList<Autos>()

        if (cursor.count == 0)
            Toast.makeText(mAtx, "No Records Found", Toast.LENGTH_LONG).show() else {
            while (cursor.moveToNext()) {
                val autos = Autos()
                autos.carid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                autos.carbrand = cursor.getString(cursor.getColumnIndex(COLUMN_Brand))
                autos.carmodel = cursor.getString(cursor.getColumnIndex(COLUMN_Model))
                autos.caryear = cursor.getString(cursor.getColumnIndex(COLUMN_Year))
                autos.carprice = cursor.getInt(cursor.getColumnIndex(COLUMN_Price))
                autosList.add(autos)

            }
            Toast.makeText(mAtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return autosList
    }

    fun addAuto (mAtx: Context,autos:Autos){
        val values = ContentValues()
            values.put(COLUMN_Brand,autos.carbrand)
            values.put(COLUMN_Model,autos.carmodel)
            values.put(COLUMN_Year,autos.caryear)
            values.put(COLUMN_Price,autos.carprice)

        val db = this.writableDatabase
        System.out.println("autosList" + values)

        try {
            db.insert(TABLE_NAME,null,values)
            //db.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_Brand, $COLUMN_Model, $COLUMN_Year, $COLUMN_Price) VALUES ('${autos.carbrand}', '${autos.carmodel}', '${autos.caryear}', ${autos.carprice});")

            Toast.makeText(mAtx,"Auto Added",Toast.LENGTH_SHORT).show()

            db?.rawQuery("SELECT * FROM $TABLE_NAME", null).use { c ->

                c?.columnNames?.let {
                    Log.d("Cursor", it.joinToString(" "))
                }
            }

        } catch (e : Exception) {
            Toast.makeText(mAtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

}