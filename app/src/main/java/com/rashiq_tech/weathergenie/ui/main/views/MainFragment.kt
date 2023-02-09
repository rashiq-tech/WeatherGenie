package com.rashiq_tech.weathergenie.ui.main.views

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.rashiq_tech.weathergenie.R
import com.rashiq_tech.weathergenie.databinding.FragmentMainBinding
import com.rashiq_tech.weathergenie.ui.main.viewmodel.MainViewModel
import com.rashiq_tech.weathergenie.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rashiq_tech.weathergenie.BuildConfig
import com.rashiq_tech.weathergenie.WeatherGenie
import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import com.rashiq_tech.weathergenie.utils.Common
import com.rashiq_tech.weathergenie.utils.Constants
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var notification: NotificationCompat.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        initViews()
        observeData()

    }

    private fun initViews() {
        binding.tvCityName.setOnClickListener {
            SelectCityDialog().show(childFragmentManager, "SelectCityDialog")
        }

        childFragmentManager.setFragmentResultListener("1001", this) { key, bundle ->
            //On city selection from dialog, cityName is saved to Preference
            //and API is called to get the weather data
            val result = bundle.getString("CITY") ?: WeatherGenie.DEFAULT_WEATHER_CITY
            Common.postStringToPreference(Constants.PREF_CITY_NAME, result, requireContext())
            viewModel.getWeatherAgainstCity(result)
        }
    }

    private fun observeData() {
        //weather details data from OpenWeatherMap API is retrieved
        //the value of cityName is loaded from Preference. If no value in Preference,
        //London as default city is loaded
        viewModel.getWeatherAgainstCity(
            Common.getStringFromPreference(
                Constants.PREF_CITY_NAME,
                requireContext()
            ) ?: WeatherGenie.DEFAULT_WEATHER_CITY
        )

        //weather data result  is observed from ViewModel and binds to the views
        viewModel.weatherInfoState.observe(requireActivity()) {
            when (it) {
                is ApiState.Success -> {
                    val data = it.data!!

                    //function to generate a local notification is called
                    sendLocalNotification(data)

                    binding.tvCityName.text = data.cityName
                    binding.tvTemp.text = String.format(
                        getString(R.string.placeholder_degree),
                        data.mainObject.temp.toString()
                    )
                    binding.tvMinTemp.text = String.format(
                        getString(R.string.placeholder_degree),
                        data.mainObject.tempMin.toString()
                    )
                    binding.tvMaxTemp.text = String.format(
                        getString(R.string.placeholder_degree),
                        data.mainObject.tempMax.toString()
                    )
                    binding.tvFeelsLikeTemp.text = String.format(
                        getString(R.string.placeholder_degree),
                        data.mainObject.feelsLike.toString()
                    )
                }
                is ApiState.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
                is ApiState.Loading -> {
                    Toast.makeText(requireContext(), "Loading.....", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun sendLocalNotification(data: WeatherInfo) {

        //if temperature of the city is less than 0 and greater than 20
        //then a local notification is loaded else nothing.
        val message: String = when {
            data.mainObject.temp < 0 -> {
                "It is freezing....!!"

            }
            data.mainObject.temp > 20 -> {
                "It is boiling....!!"
            }
            else -> {
                return
            }
        }
        notification.setContentTitle(getString(R.string.app_name) + " - " + data.cityName)
        notification.setContentText(message)

        createNotificationChannel()


        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(requireContext())
            .notify(Constants.NOTIFICATION_ID, notification.build())
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager =
                requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}