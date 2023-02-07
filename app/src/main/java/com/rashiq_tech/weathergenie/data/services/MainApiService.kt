package com.rashiq_tech.weathergenie.data.services

import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {

    @GET("weather")
    suspend fun getWeatherAgainstCity(@Query("lat") lat : Double,
                                      @Query("lon") long : Double,
                                      @Query("appid") appId : String,): Response<List<WeatherInfo>>

}