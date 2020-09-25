
package com.example.foodrunner2.fragement


import Dbasynctask
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodrunner2.Adapter.RestaurantsAdapter
import com.example.foodrunner2.R
import com.example.foodrunner2.database.ResDatabase
import com.example.foodrunner2.database.ResEntity
import com.example.foodrunner2.model.Restaurants
import kotlinx.android.synthetic.main.fragment_fav.*

/**
 * A simple [Fragment] subclass.
 */
class FavFragment : Fragment() {
    lateinit var favrecycler:RecyclerView
    lateinit var favprogress:RelativeLayout
    var reslist= arrayListOf<Restaurants>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_fav, container, false)
        favrecycler = view.findViewById(R.id.favRecycler)
        favprogress =view.findViewById(R.id.favProgressLayout)

        favprogress.visibility= View.VISIBLE

        val getFavRes:List<ResEntity> = DBGetFavRes(activity as Context).execute().get()
        for (res in getFavRes){
            reslist.add(Restaurants(res.uid,res.name,res.rating,res.price,res.image))
        }
        favrecycler.layoutManager = LinearLayoutManager(activity as Context)
        favrecycler.adapter = RestaurantsAdapter(activity as Context,reslist)
        favprogress.visibility= View.GONE
        return view
    }

    class  DBGetFavRes(val context:Context): AsyncTask<Void, Void, List<ResEntity>>() {
        val db= Room.databaseBuilder(context,ResDatabase::class.java,"res-db").build()
        override fun doInBackground(vararg params: Void?): List<ResEntity> {
            return db.Dao().getAll()
        }

    }

}
