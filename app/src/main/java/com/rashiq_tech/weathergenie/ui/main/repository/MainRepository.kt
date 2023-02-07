package com.rashiq_tech.weathergenie.ui.main.repository

import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import com.rashiq_tech.weathergenie.data.services.MainApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(val mainApiService: MainApiService) : MainRepositoryImpl {
    override suspend fun getWeatherAgainstCity(
        cityName: String,
        appId: String,
        units: String
    ): Response<WeatherInfo> = withContext(Dispatchers.IO){
        mainApiService.getWeatherAgainstCity(cityName, appId, units)
    }
}