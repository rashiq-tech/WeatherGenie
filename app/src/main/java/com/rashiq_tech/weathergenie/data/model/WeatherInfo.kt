package com.rashiq_tech.weathergenie.data.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo constructor(@SerializedName("main") val mainObject: Main,
                                   @SerializedName("name") val cityName: String){

    data class Main constructor(@SerializedName("temp") val temp: Double, @SerializedName("feels_like")val feelsLike: Double,
                                @SerializedName("temp_min") val tempMin: Double, @SerializedName("temp_max") val tempMax: Double,
                                @SerializedName("pressure") val pressure: Double, @SerializedName("humidity") val humidity: Int,
                                @SerializedName("sea_level") val seaLevel: Int, @SerializedName("grnd_level") val groundLevel: Int)

}
