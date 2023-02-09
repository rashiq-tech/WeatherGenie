package com.rashiq_tech.weathergenie.ui.main.repository

import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import retrofit2.Response

interface MainRepositoryImpl {

    suspend fun getWeatherAgainstCity(lat : String, appId : String, units: String): Response<WeatherInfo>

}