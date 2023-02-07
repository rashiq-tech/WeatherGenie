package com.rashiq_tech.weathergenie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherGenie : Application() {

    companion object {
        const val API_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
    }

}