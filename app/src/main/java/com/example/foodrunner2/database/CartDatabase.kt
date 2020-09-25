package com.example.foodrunner2.database

import android.view.Menu
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(MenuItemEntity::class),version = 2)
abstract class CartDatabase :RoomDatabase(){
    abstract fun dao():CartDao
}