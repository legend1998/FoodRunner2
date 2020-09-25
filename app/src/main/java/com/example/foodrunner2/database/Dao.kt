package com.example.foodrunner2.database

import androidx.room.*
import androidx.room.Dao
import com.example.foodrunner2.database.ResEntity


@Dao
interface Dao {
    @Query("SELECT * FROM  restaurants")
    fun getAll():List<ResEntity>

    @Query("SELECT * FROM restaurants WHERE uid=:id")
    fun Findrestaurant(id :String):ResEntity

    @Insert
    fun insertRes(res: ResEntity)

    @Delete
    fun deleteRes(res:ResEntity)

}
