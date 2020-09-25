package com.example.foodrunner2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "menu_item")
data class MenuItemEntity (
    @PrimaryKey @ColumnInfo(name="item_id") val id:String,
    @ColumnInfo(name="res_id") val resid:String,
    val name:String,
    val cost:String
)