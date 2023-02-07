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
import com.rashiq_tech.weathergenie.data.model.WeatherInfo
import com.rashiq_tech.weathergenie.utils.Constants

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        observeData()

    }

    private fun observeData() {
        viewModel.getWeatherAgainstCity("London")
        viewModel.weatherInfoState.observe(requireActivity()) {
            when (it) {
                is ApiState.Success -> {
                    val data = it.data!!

                    sendLocalNotification(data)

                    binding.tvCityName.text = "London"
                    binding.tvTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.temp.toString())
                    binding.tvMinTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.tempMin.toString())
                    binding.tvMaxTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.tempMax.toString())
                    binding.tvFeelsLikeTemp.text = String.format(getString(R.string.placeholder_degree), data.mainObject.feelsLike.toString())
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

        val message : String = when {
            data.mainObject.temp < 0 -> {
                "Its cold!"

            } data.mainObject.temp > 20 -> {
                "Its hot"
            } else -> {
                return
            }
        }

        createNotificationChannel()

        val notification = NotificationCompat.Builder(requireContext(),Constants.CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_close_white)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManger = NotificationManagerCompat.from(requireContext())

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManger.notify(Constants.NOTIF_ID,notification)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}