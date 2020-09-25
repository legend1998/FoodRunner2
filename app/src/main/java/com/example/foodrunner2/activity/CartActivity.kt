package com.example.foodrunner2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.Adapter.CartAdapter
import com.example.foodrunner2.R
import com.example.foodrunner2.database.*
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

    lateinit var restaurantName: TextView
    lateinit var cartRecycler: RecyclerView
    lateinit var button: Button
    lateinit var toolbar: Toolbar
    lateinit var progress:RelativeLayout

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        sharedPreferences =
            getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE)

        val res_name = intent.getStringExtra("res_name")
        val resid = intent.getStringExtra("res_id")

        toolbar = findViewById(R.id.cart_toolbar)
        button = findViewById(R.id.cart_button)
        cartRecycler = findViewById(R.id.cart_listview)
        restaurantName = findViewById(R.id.restaurant_name_cart)
        progress = findViewById(R.id.cart_progress)
        progress.visibility = View.GONE


        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cart"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        restaurantName.text = "Order from ${res_name}"

        val cartlist = CartAsnc(applicationContext as Context).execute().get()
        val adapter = CartAdapter(applicationContext as Context, cartlist, button)
        cartRecycler.layoutManager = LinearLayoutManager(applicationContext as Context)
        cartRecycler.adapter = adapter



        button.setOnClickListener {
            progress.visibility = View.VISIBLE

            val queue = Volley.newRequestQueue(this)

            val url = "http://13.235.250.119/v2/place_order/fetch_result/"


            val cartitem = CartAsnc(this).execute().get()

            val params = JSONObject()
            params.put("user_id", sharedPreferences.getString("user_id", "").toString())
            params.put("restaurant_id", if (resid != null) resid else "")
            params.put("total_cost", adapter.findsum().toString())

            val foodcart = JSONArray()
            for (item in cartitem) {
                foodcart.put(JSONObject().put("food_item_id", item.id))
            }

            params.put("food", foodcart)


            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.POST, url, params, Response.Listener {
                    //this is response listener....
                println(it)
                    if(it.getJSONObject("data").getBoolean("success")){
                        Toast.makeText(this,"order confirmed",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,OrderSuccess::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"something went wrong.",Toast.LENGTH_SHORT).show()
                    }

                }, Response.ErrorListener {
                    //this is error listener
                    Toast.makeText(this,"error on response.",Toast.LENGTH_SHORT).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["token"] = "24958f6a8141aa"
                        header["content-type"] = "application/json"
                        return header
                    }

                }
            queue.add(jsonObjectRequest)
        }
    }


    class CartAsnc(val context: Context) : AsyncTask<Void, Void, List<MenuItemEntity>>() {
        val db = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db").build()
        override fun doInBackground(vararg params: Void?): List<MenuItemEntity> {

            val cartList = db.dao().getAllItem()
            db.close()
            return cartList
        }

    }


}
