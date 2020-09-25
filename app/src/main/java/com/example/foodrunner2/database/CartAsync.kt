import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.foodrunner2.database.CartDatabase
import com.example.foodrunner2.database.MenuItemEntity


class CartAsync(val context:Context,val menuItemEntity: MenuItemEntity,val mode:String):AsyncTask<Void,Void,Boolean>() {


    val db = Room.databaseBuilder(context,CartDatabase::class.java,"cart-db").build()
    override fun doInBackground(vararg params: Void?): Boolean {
        when(mode){
            "insert"->{
                db.dao().insertMenuItem(menuItemEntity)
                db.close()
                return true
            }
            "delete"->{
                db.dao().deleteMenuItem(menuItemEntity)
                db.close()
                return true
            }
            "getItem"->{
                val item :MenuItemEntity?=db.dao().getItem(menuItemEntity.id)
                db.close()
                return item!=null
            }
            "deleteAll"->{
                db.dao().DeleteALLitem()
                db.close()
                return true
            }
            "getItemCount"->{
                val item =  db.dao().GetaItemCount()
                db.close()
                return item > 0
            }
        }
        return false
    }

}