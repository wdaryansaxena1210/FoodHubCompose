package com.example.foodhub_android.ui.navigation

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