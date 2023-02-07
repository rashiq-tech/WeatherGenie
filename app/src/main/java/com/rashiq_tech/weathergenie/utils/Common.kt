package com.rashiq_tech.weathergenie.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rashiq_tech.weathergenie.data.model.City
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException

class Common {

    fun getCityList(context: Context): List<City> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("cities.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("ERR", ioException.toString())
        }

        val listCountryType = object : TypeToken<List<City>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }


}