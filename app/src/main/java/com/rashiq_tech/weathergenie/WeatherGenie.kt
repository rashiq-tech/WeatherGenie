package com.rashiq_tech.weathergenie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherGenie : Application() {

    companion object {
        const val API_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
        const val WEATHER_APP_ID = "9b5ae1a801024b50acc4d3434ef4fb08"
        const val DEFAULT_WEATHER_CITY = "London"
    }

}