package com.example.foodrunner2.activity

import CartAsync
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.Adapter.DescAdapter
import com.example.foodrunner2.R
import com.example.foodrunner2.database.CartDatabase
import com.example.foodrunner2.model.RestaurantsMenu
import kotlinx.android.synthetic.main.rescard.*

class RestaurantDescription : AppCompatActivity() {

    lateinit var descRecycler: RecyclerView
    lateinit var descToolbar: Toolbar
    public lateinit var checkout: Button
    var menu_list = mutableListOf<RestaurantsMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_description)

        val intent = getIntent()
        val res_name = intent.getStringExtra("res_name")
        val resid = intent.getStringExtra("resid")



        descToolbar = findViewById(R.id.desc_toolbar)
        descRecycler = findViewById(R.id.detailRecycler)
        checkout = findViewById(R.id.desc_checkout)

        checkout.visibility = View.GONE

        setSupportActionBar(descToolbar)
        supportActionBar?.title = res_name
        supportActionBar?.subtitle = "Menu List"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val url = "http://13.235.250.119/v2/restaurants/fetch_result/${resid}"

        val queue = Volley.newRequestQueue(applicationContext)

        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                //this is response listener

                val data = it.getJSONObject("data")
                val dataArray = data.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val item = dataArray.getJSONObject(i)
                    menu_list.add(
                        RestaurantsMenu(
                            item.getString("id"),
                            item.getString("restaurant_id"),
                            item.getString("name"),
                            item.getString("cost_for_one")
                        )
                    )
                }
                val adapter = DescAdapter(applicationContext as Context, menu_list,checkout)
                descRecycler.adapter = adapter
                descRecycler.layoutManager = LinearLayoutManager(applicationContext as Context)

            },
                Response.ErrorListener {
                    //this is error listener
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["content-type"] = "application/json"
                    header["token"] = "24958f6a8141aa"
                    return header
                }
            }

        checkout.setOnClickListener {
            val intent = Intent(this@RestaurantDescription,CartActivity::class.java)
            intent.putExtra("res_name",res_name)
            intent.putExtra("res_id",resid)
            startActivity(intent)
        }

        queue.add(jsonObjectRequest)
    }


    override fun onBackPressed() {
        DeleteAllCart(applicationContext as Context).execute()
        finish()
    }

    class DeleteAllCart(context: Context) : AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.dao().DeleteALLitem()
            return true
        }
    }
}
