package com.example.foodrunner2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.model.Historydata

class HistoryAdapter(val context: Context, val itemList: List<Historydata>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val res_name:TextView = view.findViewById(R.id.history_res_name)
        val order_at:TextView = view.findViewById(R.id.history_order_at)
        val res_cost:TextView = view.findViewById(R.id.history_res_cost)
        val ItemRecycler:RecyclerView = view.findViewById(R.id.history_item_recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =  LayoutInflater.from(context).inflate(R.layout.historymenuitem,parent,false)
        return ViewHolder(view)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.res_name.text = item.restaurant_name
        holder.res_cost.text = item.total_cost
        holder.order_at.text = item.order_placed_at
        holder.ItemRecycler.layoutManager = LinearLayoutManager(context)
        holder.ItemRecycler.adapter  = HistoryMenuAdapter(context,item.food_Item)


    }
}