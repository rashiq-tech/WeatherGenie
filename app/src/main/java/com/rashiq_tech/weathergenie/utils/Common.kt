package com.rashiq_tech.weathergenie.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rashiq_tech.weathergenie.WeatherGenie
import com.rashiq_tech.weathergenie.data.model.City
import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class Common {

    companion object {

        fun postStringToPreference(key: String, value: String, context: Context) {
            val sharedPreference =
                context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getStringFromPreference(key: String, context: Context): String? {
            return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                .getString(key, null)
        }

    }

}