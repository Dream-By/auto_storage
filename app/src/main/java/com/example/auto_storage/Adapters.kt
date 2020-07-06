package com.example.auto_storage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_editor.view.*
import kotlinx.android.synthetic.main.lo_auto.view.*

class AutoAdapter(mAtx:Context, val auto:ArrayList<Autos>) : RecyclerView.Adapter<AutoAdapter.ViewHolder>() {

    val mAtx = mAtx

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val textCarBrand = itemView.textView1
        val textCarModel = itemView.textView2
        val textYear = itemView.textView3
        val textPrice = itemView.textView4


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_auto,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {

        return auto.size

    }

    override fun onBindViewHolder(holder: AutoAdapter.ViewHolder, position: Int) {

        val autos : Autos = auto[position]

        holder.textCarBrand.text = autos.carbrand
        holder.textCarModel.text = autos.carmodel
        holder.textYear.text = autos.caryear
        holder.textPrice.text = autos.carprice.toString()

    }
}