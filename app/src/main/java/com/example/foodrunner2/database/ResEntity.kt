package com.example.foodrunner2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Restaurants")
data class ResEntity (
    @PrimaryKey val uid :String,
    @ColumnInfo(name="Res_name") val name:String,
    @ColumnInfo(name="price") val price:String,
    @ColumnInfo(name="rating") val rating:String,
    @ColumnInfo(name="image") val image:String
)

