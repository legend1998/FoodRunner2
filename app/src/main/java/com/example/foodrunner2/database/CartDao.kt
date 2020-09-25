package com.example.foodrunner2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    fun insertMenuItem(menuItemEntity: MenuItemEntity)

    @Delete
    fun deleteMenuItem(menuItemEntity: MenuItemEntity)

    @Query("SELECT COUNT(*) FROM menu_item")
    fun GetaItemCount():Int

    @Query("DELETE FROM menu_item")
    fun DeleteALLitem()

    @Query("SELECT * FROM menu_item WHERE item_id=:id")
    fun getItem(id:String):MenuItemEntity

    @Query("SELECT * FROM menu_item")
    fun getAllItem():List<MenuItemEntity>
}