package com.example.foodrunner2.Adapter

import Dbasynctask
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.activity.RestaurantDescription
import com.example.foodrunner2.database.ResEntity
import com.example.foodrunner2.model.Restaurants
import com.squareup.picasso.Picasso

class RestaurantsAdapter(val context: Context, val reslist: ArrayList<Restaurants>) :
    RecyclerView.Adapter<RestaurantsAdapter.restaurantsHolder>() {

    class restaurantsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.res_image)
        val name: TextView = view.findViewById(R.id.res_name)
        val price: TextView = view.findViewById(R.id.res_price)
        val fav: ImageView = view.findViewById(R.id.fav_res)
        val rating: TextView = view.findViewById(R.id.res_rating)
        val rescard :RelativeLayout=view.findViewById((R.id.rescard))

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): restaurantsHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rescard, parent, false)
        return restaurantsHolder(view)
    }

    override fun getItemCount(): Int {
        return reslist.size
    }

    override fun onBindViewHolder(holder: restaurantsHolder, position: Int) {
        val restaurants = reslist[position]
        holder.name.text = restaurants.name
        holder.price.text = restaurants.cost_for_one + "/person"
        holder.rating.text = restaurants.rating
        Picasso.get().load(restaurants.image_url).into(holder.image)

        val resEntity =ResEntity(restaurants.id,restaurants.name,restaurants.cost_for_one,restaurants.rating,restaurants.image_url)


        val isfav =Dbasynctask(context,resEntity,1).execute()
        if(isfav.get()){
            holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp)
        }else{
            holder.fav.setImageResource(R.drawable.ic_fav_res_border_pink_24dp)
        }

        holder.rescard.setOnClickListener {
            val intent = Intent(context,RestaurantDescription::class.java)
            intent.putExtra("resid",restaurants.id)
            intent.putExtra("res_name",restaurants.name)
            context.startActivity(intent)

        }

        holder.fav.setOnClickListener {
            val checkfav = Dbasynctask(context,resEntity,1).execute()
            if(checkfav.get()){
                Dbasynctask(context,resEntity,3).execute()
                it.setBackgroundResource(R.drawable.ic_fav_res_border_pink_24dp)
            }else{
                Dbasynctask(context,resEntity,2).execute()
                it.setBackgroundResource(R.drawable.ic_favorite_black_24dp)
            }
            Toast.makeText(
                context,
                "added to favourite ${restaurants.name} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}