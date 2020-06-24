package com.example.auto_storage

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_editor.*


class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        editCarBrand.setText("")
        editCarModel.setText("")
        editYear.setText("")
        editPrice.setText("")

        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonCancel:Button = findViewById(R.id.buttonCancel)



        buttonAdd.setOnClickListener(View.OnClickListener {
            if (editCarBrand.text.isEmpty() && editCarModel.text.isEmpty() && editPrice.text.isEmpty() && editYear.text.isEmpty()) {
                Toast.makeText(this,"Enter fields",Toast.LENGTH_LONG).show()
                editCarBrand.requestFocus()
            } else {
                val autos = Autos(carid = -1,carbrand = "",carmodel = "",caryear = "",carprice = "")
                autos.carbrand = editCarBrand.text.toString()
                autos.carmodel =editCarModel.text.toString()
                autos.caryear = editYear.text.toString()
                autos.carprice = editPrice.text.toString()
                MainActivity.dbHelper.addAuto(this,autos)
                clearEdits()
                editCarBrand.requestFocus()
            }

        })

        buttonCancel.setOnClickListener(View.OnClickListener {
            clearEdits()
            finish()
        })

    }

    private fun clearEdits() {
        editCarBrand.text.clear()
        editCarModel.text.clear()
        editPrice.text.clear()
        editYear.text.clear()

    }
}