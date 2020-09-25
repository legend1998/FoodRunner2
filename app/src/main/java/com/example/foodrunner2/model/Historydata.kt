package com.example.foodrunner2.model

import org.json.JSONArray

data class Historydata (
    val order_id :String,
    val restaurant_name: String,
    val total_cost :String,
    val order_placed_at :String,
    val food_Item :ArrayList<RestaurantsMenu>
)