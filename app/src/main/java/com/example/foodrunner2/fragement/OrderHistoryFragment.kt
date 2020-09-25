package com.example.foodrunner2.fragement


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.Adapter.HistoryAdapter

import com.example.foodrunner2.R
import com.example.foodrunner2.model.Historydata
import com.example.foodrunner2.model.RestaurantsMenu

/**
 * A simple [Fragment] subclass.
 */
class OrderHistoryFragment : Fragment() {
    lateinit var history_recycler: RecyclerView
    lateinit var progress:RelativeLayout
    var OrderList = arrayListOf<Historydata>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        progress = view.findViewById(R.id.order_history_progress)
        progress.visibility = View.VISIBLE
        val sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.sharedPreference),
            Context.MODE_PRIVATE
        )

        val userId = sharedPreferences?.getString("user_id", "")

        history_recycler = view.findViewById(R.id.history_recycler)


        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v2/orders/fetch_result/${userId}"

        val jsonRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                if (it.getJSONObject("data").getBoolean("success")) {

                    val data = it.getJSONObject("data").getJSONArray("data")

                    for (i in 0 until data.length()) {

                        val resitem = data.getJSONObject(i)

                        val food_itemList = resitem.getJSONArray("food_items")
                        val foodlist = arrayListOf<RestaurantsMenu>()

                        for (j in 0 until food_itemList.length()) {
                            val temp = food_itemList.getJSONObject(j)
                            foodlist.add(
                                RestaurantsMenu(
                                    temp.getString("food_item_id"),
                                    "",
                                    temp.getString("name"),
                                    temp.getString("cost")
                                )
                            )
                        }
                        OrderList.add(
                            Historydata(
                                resitem.getString("order_id"),
                                resitem.getString("restaurant_name"),
                                resitem.getString("total_cost"),
                                resitem.getString("order_placed_at"),
                                foodlist
                            )
                        )
                    }
                    println(OrderList)
                    history_recycler.adapter = HistoryAdapter(activity as Context,OrderList)
                    history_recycler.layoutManager = LinearLayoutManager(activity as Context)

                    progress.visibility = View.GONE
                } else {
                    Toast.makeText(
                        activity as Context,
                        "something went wrong!${it.getJSONObject("data")
                            .getString("errorMessage")}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //this is response listener
            }, Response.ErrorListener {
                //this is error response listener
                Toast.makeText(activity as Context, "something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["token"] = "24958f6a8141aa"
                    header["content-type"] = "application/json"
                    return header
                }
            }
        queue.add(jsonRequest)
        return view
    }
}
