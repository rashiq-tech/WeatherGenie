package com.rashiq_tech.weathergenie.data.services

import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface MainApiService {

    @GET("weather")
    suspend fun getWeatherAgainstCity(@Query("q") cityName : String,
                                      @Query("appid") appId : String,
                                      @Query("units") unit : String): Response<WeatherInfo>

}