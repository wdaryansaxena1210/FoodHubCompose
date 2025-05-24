package com.example.foodhub_android.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.example.foodhub_android.data.models.FoodItem
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

val foodItemNavType = object : NavType<FoodItem>(isNullableAllowed = false) {

    //Retrieves the JSON string from the Bundle using the key, then parses it back into a FoodItem.
    //key = "foodItem";
    override fun get(bundle: Bundle, key: String): FoodItem? {
        return parseValue(bundle.getString(key).toString())
    }

    //Converts the JSON string into a FoodItem using Kotlin serialization.
    //value = """     "arModelUrl":"https://example.com/model.glb", "...":""   """; //actual json
    override fun parseValue(value: String): FoodItem {
        val foodItem = Json.decodeFromString(FoodItem.serializer(), value)
        return foodItem.copy(imageUrl = URLDecoder.decode(foodItem.imageUrl))
    }

    override fun serializeAsValue(value: FoodItem): String {
        return Json.encodeToString(FoodItem.serializer(), value.copy(
            imageUrl = URLEncoder.encode(value.imageUrl, "UTF-8"),
        ))
    }

    override fun put(bundle: Bundle, key: String, value: FoodItem) {
        bundle.putString(key, serializeAsValue(value))
    }

}