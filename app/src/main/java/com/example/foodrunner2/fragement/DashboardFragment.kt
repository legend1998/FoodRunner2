package com.example.foodrunner2.fragement


import android.app.Activity
import android.app.AlertDialog
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.Adapter.RestaurantsAdapter

import com.example.foodrunner2.R
import com.example.foodrunner2.model.Restaurants
import com.example.foodrunner2.util.ConnectionManger
import org.json.JSONObject
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var dashbaordProgress: RelativeLayout
    var reslist = arrayListOf<Restaurants>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashbaordProgress = view.findViewById(R.id.dashboardProgress)
        recyclerView = view.findViewById(R.id.dashboard_recycler)

        val linearLayoutManager = LinearLayoutManager(activity as Context)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        val queue = Volley.newRequestQueue(activity as Context)

        if (ConnectionManger().checkConnectivity(activity as Context)) {
            try {
                dashbaordProgress.visibility = View.VISIBLE
                val jsonObjectRequest =
                    object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                        // on success listener
                        val data = it.getJSONObject("data")
                        val success = it.getJSONObject("data").getBoolean("success")
                        if(success){
                            val dataArray = data.getJSONArray("data")

                            for (i in 0 until dataArray.length()) {
                                val res = dataArray.getJSONObject(i)
                                reslist.add(
                                    Restaurants(
                                        res.getString("id"),
                                        res.getString("name"),
                                        res.getString("rating"),
                                        res.getString("cost_for_one"),
                                        res.getString("image_url")
                                    )
                                )
                            }
                            val resAdapter = RestaurantsAdapter(activity as Context, reslist)
                            recyclerView.layoutManager = linearLayoutManager
                            recyclerView.adapter = resAdapter

                            dashbaordProgress.visibility = View.GONE
                        }else{
                            Toast.makeText(context,"error ${it.getJSONObject("data").getString("errorMessage")}",Toast.LENGTH_SHORT).show()
                        }

                    }, Response.ErrorListener {
                        //listen to error
                        dashbaordProgress.visibility = View.GONE
                        println("error")
                    }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val header = HashMap<String, String>()
                            header["token"] = "24958f6a8141aa"
                            header["content-type"] = "application/json"
                            return header
                        }
                    }
                queue.add(jsonObjectRequest)
            } catch (e: Exception) {
                println(e)
            }
        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setMessage("Internet is not connected!")
            dialog.setPositiveButton("Open Setting") { text, Listener ->
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                activity?.finish()
            }
            dialog.setNegativeButton("close") { text, Listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.show()
        }

        return view
    }


}
