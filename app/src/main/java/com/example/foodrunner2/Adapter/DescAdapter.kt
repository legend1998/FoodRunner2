package com.example.foodrunner2.Adapter

import CartAsync
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.database.MenuItemEntity
import com.example.foodrunner2.model.RestaurantsMenu

class DescAdapter(
    val context: Context,
    val menulist: List<RestaurantsMenu>,
    val checkOutButton: Button
) :

    RecyclerView.Adapter<DescAdapter.DescViewholder>() {

    init {
        setHasStableIds(true)
    }

    class DescViewholder(view: View) : RecyclerView.ViewHolder(view) {

        val sl_no: TextView = view.findViewById(R.id.menu_serialNO)
        val menu_name: TextView = view.findViewById(R.id.menu_name)
        val menu_price: TextView = view.findViewById(R.id.menu_price)
        val menu_button: Button = view.findViewById(R.id.menu_button)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.menurestaurant, parent, false)

        return DescViewholder(view)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return menulist.size
    }


    override fun onBindViewHolder(holder: DescViewholder, position: Int) {
        val menu = menulist[position]

        holder.sl_no.text = "${position+1}"
        holder.menu_name.text = menu.name
        holder.menu_price.text = menu.cost

        val mitem = MenuItemEntity(menu.id, menu.resid, menu.name, menu.cost)
        if(CartAsync(context, mitem, "getItem").execute().get()){
            holder.menu_button.text = context.getString(R.string.remove)
        }else{
            holder.menu_button.text = context.getString(R.string.add)
        }



        holder.menu_button.setOnClickListener {
            val menuitem = MenuItemEntity(menu.id, menu.resid, menu.name, menu.cost)
            val isAdded = CartAsync(context, menuitem, "getItem").execute().get()

            if (isAdded) {
                holder.menu_button.setText(R.string.add)
                it.setBackgroundResource(R.drawable.roundedlightbackground)
                CartAsync(context, menuitem, mode = "delete").execute()

            } else {
                holder.menu_button.setText(R.string.remove)
                it.setBackgroundResource(R.drawable.roundedbackground)
                CartAsync(context, menuitem, mode = "insert").execute()
            }

            val emptyCart = CartAsync(context, menuitem, "getItemCount").execute().get()
            if (!emptyCart) {
                checkOutButton.visibility = View.GONE
            } else {
                checkOutButton.visibility = View.VISIBLE

            }
        }
    }
}