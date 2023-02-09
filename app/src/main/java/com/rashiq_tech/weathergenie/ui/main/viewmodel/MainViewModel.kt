package com.rashiq_tech.weathergenie.ui.main.viewmodel

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashiq_tech.weathergenie.WeatherGenie
import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import com.rashiq_tech.weathergenie.ui.main.repository.MainRepository
import com.rashiq_tech.weathergenie.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    val weatherInfoState: MutableLiveData<ApiState<WeatherInfo>> = MutableLiveData()

    val cityList: MutableLiveData<List<String>> = MutableLiveData()

    fun getWeatherAgainstCity(cityName: String) {
        weatherInfoState.postValue(ApiState.Loading())
        viewModelScope.launch {
            try {
                val response = mainRepository.getWeatherAgainstCity(cityName, WeatherGenie.WEATHER_APP_ID, "metric")
                weatherInfoState.postValue(ApiState.Success(response.body()!!))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> weatherInfoState.postValue(ApiState.Error("Network Failure " + ex.localizedMessage))
                    is NetworkErrorException -> weatherInfoState.postValue(ApiState.Error("No Internet Connection.."))
                    is NullPointerException -> weatherInfoState.postValue(ApiState.Error("No data available in cache.."))
                    else -> weatherInfoState.postValue(ApiState.Error("Conversion Error"))
                }
            }
        }
    }

    fun getCityList(){
        cityList.postValue(arrayListOf("Yakutsk", "Las Vegas", "Cairo", "Delhi", "London", "Dubai", "Bangkok", "Glasgow", "Paris", "Qatar",
        "Tokyo", "Shanghai", "Sao Paulo", "Mexico City", "Cairo", "Dhaka", "Mumbai", "Beijing", "Osaka",
        "Moscow"))
    }

}