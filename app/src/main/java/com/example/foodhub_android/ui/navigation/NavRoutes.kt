package com.example.foodhub_android.ui.navigation

import com.example.foodhub_android.data.models.FoodItem
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object Home

@Serializable
object AuthScreen

@Serializable
data class RestaurantDetails(
    val restaurantId: String,
    val name : String,
    val restaurantImageUrl : String,
//    val restaurantDescription: String
)

@Serializable
data class FoodDetails(val foodItem: FoodItem)

@Serializable
object Cart

@Serializable
object Test
