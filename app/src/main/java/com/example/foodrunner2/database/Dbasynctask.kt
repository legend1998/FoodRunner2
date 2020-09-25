import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.foodrunner2.database.ResDatabase
import com.example.foodrunner2.database.ResEntity

 class Dbasynctask(val context: Context, val resEntity: ResEntity, val mode:Int):
    AsyncTask<Void, Void, Boolean>(){
    val db = Room.databaseBuilder(context, ResDatabase::class.java,"res-db").build()

    override fun doInBackground(vararg params: Void?): Boolean {
        when(mode){
            1 ->{
                val res: ResEntity? = db.Dao().Findrestaurant(resEntity.uid)
                db.close()
                return res!=null
            }
            2->{
                db.Dao().insertRes(resEntity)
                db.close()
                return true

            }
            3->{
                db.Dao().deleteRes(resEntity)
                db.close()
                return true
            }
            4->{
                db.Dao().getAll()
                db.close()
                return true
            }

        }

        return  false
    }

}