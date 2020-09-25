package com.example.foodrunner2.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ResEntity::class),version = 2)
abstract class ResDatabase:RoomDatabase() {
    abstract fun Dao():Dao
}
