package com.example.foodrunner2.Adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.model.RestaurantsMenu

class HistoryMenuAdapter(val context: Context, val Foodlist: ArrayList<RestaurantsMenu>) :
    RecyclerView.Adapter<HistoryMenuAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        val serial:TextView = view.findViewById(R.id.history_menu_serial)
        val name:TextView = view.findViewById(R.id.history_menu_name)
        val price:TextView = view.findViewById(R.id.history_menu_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.historymenu,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return Foodlist.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = Foodlist[position]
       holder.serial.text = "${position+1}"
        holder.name.text = item.name
        holder.price.text = item.cost
    }
}