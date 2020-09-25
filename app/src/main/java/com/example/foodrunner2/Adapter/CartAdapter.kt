package com.example.foodrunner2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.database.MenuItemEntity

class CartAdapter(val context: Context, val CartList: List<MenuItemEntity>,val totalButon:Button) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sl_no: TextView = view.findViewById(R.id.menu_serialNO)
        val menu_name: TextView = view.findViewById(R.id.menu_name)
        val menu_price: TextView = view.findViewById(R.id.menu_price)
        val menu_button: Button = view.findViewById(R.id.menu_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.menurestaurant,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
      return CartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = CartList[position]
       holder.sl_no.text = "%d".format(position+1)
        holder.menu_name.text = item.name
        holder.menu_price.text = item.cost
        holder.menu_button.visibility = View.GONE

        totalButon.setText("Place Order (Total price %d)".format(findsum()))
    }

    fun findsum():Int{
        var sum=0
        for (i in 0 until CartList.size){
            sum+=CartList[i].cost.toInt()
        }
        return sum
    }
}