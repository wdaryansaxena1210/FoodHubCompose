package com.example.foodhub_android.data

import android.content.Context
import android.content.SharedPreferences

class FoodHubSession(val context : Context){

    //get reference to a PRIVATE "XML" FILE foohub.xml stored at:
    // inside emulator -> "/data/data/your.package.name/shared_prefs/"
    //this file is INSIDE the EMULATOR
    val foodhubFile_sharedPres : SharedPreferences =
        context.getSharedPreferences("foodhub", Context.MODE_PRIVATE)

    fun storeToken(token : String){
        //edit the PRIVATE FILE foodhub
        foodhubFile_sharedPres.edit().putString("token", token).apply()
    }

    fun getToken() : String?{
        //access the PRIVATE FILE foodhub and check for 'token' key
        foodhubFile_sharedPres.getString("token", null)?.let {
            return it
        }

        return null
    }
}
